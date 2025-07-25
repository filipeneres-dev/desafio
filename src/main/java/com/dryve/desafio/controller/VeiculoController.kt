package com.dryve.desafio.controller

import VeiculoRequest
import com.dryve.desafio.domain.Veiculo
import com.dryve.desafio.dto.VeiculoDTO
import com.dryve.desafio.service.VeiculoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.UUID

@RestController
@RequestMapping("/veiculos")
class VeiculoController(
    private val veiculoService: VeiculoService
) {

    @GetMapping("/{placa}")
    fun buscarPorPlaca(@PathVariable placa: String): ResponseEntity<VeiculoDTO> {
        val dto = veiculoService.buscarPorPlaca(placa)
        return ResponseEntity.ok(dto)
    }

    @GetMapping("/marca/{idMarca}")
    fun listarPorMarca(
        @PathVariable idMarca: UUID,
        pageable: Pageable
    ): Page<Veiculo> {
        return veiculoService.listarPorMarca(idMarca, pageable)
    }

    @PostMapping
    fun salvar(@RequestBody veiculo: VeiculoRequest): ResponseEntity<Veiculo> {
        val salvo = veiculoService.cadastrar(veiculo)
        return ResponseEntity.ok(salvo)
    }
}