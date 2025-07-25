package com.dryve.desafio.service

import com.dryve.desafio.dto.MarcaComTotalModelosDTO
import com.dryve.desafio.repository.MarcaRepository
import org.springframework.stereotype.Service

@Service
class MarcaService(
    private val marcaRepository: MarcaRepository
) {
    fun listarComTotalModelos(): List<MarcaComTotalModelosDTO> {
        return marcaRepository.listarMarcasComTotalModelos()
    }
}
