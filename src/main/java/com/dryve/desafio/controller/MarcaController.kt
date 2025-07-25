package com.dryve.desafio.controller

import com.dryve.desafio.dto.MarcaComTotalModelosDTO
import com.dryve.desafio.service.MarcaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/marcas")
class MarcaController(
    private val marcaService: MarcaService
) {
    @GetMapping("/com-total-modelos")
    fun listarComTotalModelos(): ResponseEntity<List<MarcaComTotalModelosDTO>> {
        val resultado = marcaService.listarComTotalModelos()
        return ResponseEntity.ok(resultado)
    }
}
