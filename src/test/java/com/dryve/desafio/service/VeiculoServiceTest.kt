package com.dryve.desafio.service

import VeiculoRequest
import com.dryve.desafio.client.FipeClient
import com.dryve.desafio.domain.Marca
import com.dryve.desafio.domain.Modelo
import com.dryve.desafio.domain.Veiculo
import com.dryve.desafio.dto.FipeResponse
import com.dryve.desafio.event.VeiculoEventPublisher
import com.dryve.desafio.repository.MarcaRepository
import com.dryve.desafio.repository.ModeloRepository
import com.dryve.desafio.repository.VeiculoRepository
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal
import java.util.*

class VeiculoServiceTest {

    private val veiculoRepository: VeiculoRepository = mockk()
    private val modeloRepository: ModeloRepository = mockk()
    private val marcaRepository: MarcaRepository = mockk()
    private val fipeClient: FipeClient = mockk()
    private val eventPublisher: VeiculoEventPublisher = mockk(relaxed = true)

    private lateinit var veiculoService: VeiculoService

    @BeforeEach
    fun setup() {
        veiculoService = VeiculoService(
            veiculoRepository,
            modeloRepository,
            marcaRepository,
            fipeClient,
            eventPublisher
        )
    }

    @Test
    fun `deve cadastrar veiculo com dados validos`() {
        val request = veiculoRequest()

        every { veiculoRepository.findByPlacaIgnoreCase(any()) } returns null

        every { fipeClient.consultarValor(any()) } returns fipeResponse()

        every { marcaRepository.findFirstByNomeIgnoreCase(any()) } returns null
        every { marcaRepository.save(any()) } returns Marca(nome = "Chevrolet")

        every { modeloRepository.findFirstByNomeIgnoreCase(any()) } returns null
        every { modeloRepository.save(any()) } answers { firstArg() }

        every { veiculoRepository.save(any()) } answers {
            firstArg<Veiculo>().copy(id = UUID.randomUUID(), modelo = Modelo(id = UUID.randomUUID()))
        }

        val salvo = veiculoService.cadastrar(request)

        assertEquals("ABC1234", salvo.placa)
        assertEquals(BigDecimal("35000.00"), salvo.precoFipe)
        assertNotNull(salvo.id)
    }

    @Test
    fun `nao deve cadastrar veiculo com placa ja existente`() {
        val request = veiculoRequest()
        every { veiculoRepository.findByPlacaIgnoreCase(any()) } returns Veiculo(
            placa = "ABC1234",
            ano = 2022,
            precoAnuncio = BigDecimal("40000"),
            precoFipe = BigDecimal("35000"),
            modelo = modelo()
        )

        val exception = assertThrows(ResponseStatusException::class.java) {
            veiculoService.cadastrar(request)
        }

        assertEquals(HttpStatus.CONFLICT, exception.statusCode)
    }

    @Test
    fun `nao deve cadastrar veiculo se fipe nao encontrar`() {
        val request = veiculoRequest()
        every { veiculoRepository.findByPlacaIgnoreCase(any()) } returns null
        every { fipeClient.consultarValor(any()) } returns fipeResponseComErro()

        val exception = assertThrows(ResponseStatusException::class.java) {
            veiculoService.cadastrar(request)
        }

        assertEquals(HttpStatus.BAD_REQUEST, exception.statusCode)
    }

    @Test
    fun `deve retornar DTO ao buscar veiculo por placa existente`() {
        val modelo = modelo()
        val veiculo = Veiculo(
            id = UUID.randomUUID(),
            placa = "ABC1234",
            precoAnuncio = BigDecimal("40000"),
            precoFipe = BigDecimal("35000"),
            ano = 2022,
            modelo = modelo
        )

        every { veiculoRepository.findByPlacaIgnoreCase("ABC1234") } returns veiculo

        val resultado = veiculoService.buscarPorPlaca("ABC1234")

        assertEquals(veiculo.id, resultado.id)
        assertEquals("ABC1234", resultado.placa)
        assertEquals(BigDecimal("40000"), resultado.precoAnuncio)
        assertEquals(BigDecimal("35000"), resultado.precoFipe)
        assertEquals(2022, resultado.ano)
        assertEquals(modelo.nome, resultado.modelo)
        assertEquals(modelo.marca.nome, resultado.marca)
    }

    @Test
    fun `deve lançar exceção se veiculo com placa nao for encontrado`() {
        every { veiculoRepository.findByPlacaIgnoreCase("XYZ9999") } returns null

        val exception = assertThrows(ResponseStatusException::class.java) {
            veiculoService.buscarPorPlaca("XYZ9999")
        }

        assertEquals(HttpStatus.NOT_FOUND, exception.statusCode)
        assertTrue(exception.reason!!.contains("Veículo com placa XYZ9999 não encontrado"))
    }

    @Test
    fun `deve publicar evento apos cadastro com sucesso`() {
        val request = veiculoRequest()

        every { veiculoRepository.findByPlacaIgnoreCase(any()) } returns null
        every { fipeClient.consultarValor(any()) } returns fipeResponse()
        every { marcaRepository.findFirstByNomeIgnoreCase(any()) } returns null
        every { marcaRepository.save(any()) } returns Marca(nome = "Chevrolet")
        every { modeloRepository.findFirstByNomeIgnoreCase(any()) } returns null
        every { modeloRepository.save(any()) } answers { firstArg() }

        val modeloComId = Modelo(id = UUID.randomUUID())
        val veiculoSalvo = Veiculo(
            id = UUID.randomUUID(),
            placa = request.placa,
            precoAnuncio = request.precoAnuncio,
            precoFipe = BigDecimal("35000.00"),
            ano = request.ano,
            modelo = modeloComId
        )
        every { veiculoRepository.save(any()) } returns veiculoSalvo

        veiculoService.cadastrar(request)

        verify(exactly = 1) {
            eventPublisher.publicar(
                withArg {
                    assertEquals(request.placa, it.placa)
                    assertEquals(request.precoAnuncio, it.precoAnuncio)
                    assertEquals(BigDecimal("35000.00"), it.precoFipe)
                    assertEquals(request.ano, it.ano)
                    assertEquals(modeloComId.id, it.modeloId)
                    assertNotNull(it.id)
                    assertNotNull(it.dataCadastro)
                }
            )
        }
    }

    companion object {
        fun veiculoRequest() = VeiculoRequest(
            placa = "ABC1234",
            precoAnuncio = BigDecimal("40000"),
            ano = 2022,
            idMarca = 21,
            idModelo = 123
        )

        fun fipeResponse() = FipeResponse(
            valor = "R$ 35.000,00",
            marca = "Chevrolet",
            modelo = "Onix",
            anoModelo = 2022,
            combustivel = "Gasolina",
            codigoFipe = "001234",
            mesReferencia = "julho de 2025",
            autenticacao = "abc123",
            tipoVeiculo = 1,
            siglaCombustivel = "G",
            dataConsulta = "2025-07-25"
        )

        fun fipeResponseComErro() = FipeResponse(
            valor = null,
            marca = null,
            modelo = null,
            anoModelo = null,
            combustivel = "",
            codigoFipe = "",
            mesReferencia = "",
            autenticacao = "",
            tipoVeiculo = 1,
            siglaCombustivel = "",
            dataConsulta = "",
            erro = "Veículo não encontrado"
        )

        fun modelo(): Modelo {
            val marca = Marca(nome = "Chevrolet")
            return Modelo(nome = "Onix", marca = marca)
        }
    }
}
