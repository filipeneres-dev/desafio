package com.dryve.desafio.service

import VeiculoRequest
import com.dryve.desafio.client.FipeClient
import com.dryve.desafio.domain.Marca
import com.dryve.desafio.domain.Modelo
import com.dryve.desafio.domain.Veiculo
import com.dryve.desafio.dto.FipeRequest
import com.dryve.desafio.dto.VeiculoDTO
import com.dryve.desafio.event.VeiculoCadastradoEvent
import com.dryve.desafio.event.VeiculoEventPublisher
import com.dryve.desafio.repository.MarcaRepository
import com.dryve.desafio.repository.ModeloRepository
import com.dryve.desafio.repository.VeiculoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal
import java.util.*

@Service
class VeiculoService(
    private val veiculoRepository: VeiculoRepository,
    private val modeloRepository: ModeloRepository,
    private val marcaRepository: MarcaRepository,
    private val fipeClient: FipeClient,
    private val veiculoEventPublisher: VeiculoEventPublisher
) {

    @Transactional
    fun cadastrar(veiculo: VeiculoRequest): Veiculo {

        veiculoRepository.findByPlacaIgnoreCase(veiculo.placa)?.let {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Veículo com placa '${veiculo.placa}' já cadastrado")
        }

        val fipeRequest = construirFipeRequest(veiculo)

        val fipeResponse = fipeClient.consultarValor(fipeRequest)

        if (fipeResponse.erro != null || fipeResponse.valor == null) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Veículo não encontrado na FIPE.")
        }

        val marcaSalva = marcaRepository.findFirstByNomeIgnoreCase(fipeResponse.marca!!)
            ?: marcaRepository.save(Marca(nome = fipeResponse.marca))

        val modeloSalvo = modeloRepository.findFirstByNomeIgnoreCase(fipeResponse.modelo!!)
            ?: modeloRepository.save(Modelo(
                nome = fipeResponse.modelo,
                marca = marcaSalva
            ))

        val precoFipe = formatarPrecoFipe(fipeResponse.valor!!)

        val veiculoComFipe = Veiculo(
            placa = veiculo.placa,
            ano = fipeResponse.anoModelo!!,
            precoAnuncio = veiculo.precoAnuncio,
            precoFipe = precoFipe,
            modelo = modeloSalvo,
        )

        val salvo = veiculoRepository.save(veiculoComFipe)

        val evento = VeiculoCadastradoEvent(
            id = salvo.id!!,
            placa = salvo.placa,
            precoAnuncio = salvo.precoAnuncio,
            precoFipe = salvo.precoFipe,
            ano = salvo.ano,
            dataCadastro = salvo.dataCadastro,
            modeloId = salvo.modelo.id!!
        )

        veiculoEventPublisher.publicar(evento)
        return salvo
    }

    fun listarPorMarca(idMarca: UUID, pageable: Pageable): Page<Veiculo> {
        return veiculoRepository.findAllByModelo_Marca_Id(idMarca, pageable)
    }

    fun buscarPorPlaca(placa: String): VeiculoDTO {
        val veiculo = veiculoRepository.findByPlacaIgnoreCase(placa)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo com placa $placa não encontrado")

        return VeiculoDTO(
            id = veiculo.id!!,
            placa = veiculo.placa,
            precoAnuncio = veiculo.precoAnuncio,
            ano = veiculo.ano,
            precoFipe = veiculo.precoFipe,
            dataCadastro = veiculo.dataCadastro,
            modelo = veiculo.modelo.nome,
            marca = veiculo.modelo.marca.nome
        )
    }

    companion object {
        fun construirFipeRequest(req: VeiculoRequest): FipeRequest = FipeRequest(
            codigoTabelaReferencia = 323,
            codigoTipoVeiculo = 1,
            codigoMarca = req.idMarca,
            codigoTipoCombustivel = 1,
            anoModelo = req.ano,
            codigoModelo = req.idModelo,
            tipoConsulta = "tradicional"
        )

        fun formatarPrecoFipe(valor: String): BigDecimal {
            return valor.replace("R$", "")
                .replace(".", "")
                .replace(",", ".")
                .trim()
                .toBigDecimal()
        }
    }
}
