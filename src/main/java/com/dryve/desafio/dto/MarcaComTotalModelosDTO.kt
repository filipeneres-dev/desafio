package com.dryve.desafio.dto

import java.util.*

data class MarcaComTotalModelosDTO(
    val id: UUID,
    val nome: String,
    val totalModelos: Long
)
