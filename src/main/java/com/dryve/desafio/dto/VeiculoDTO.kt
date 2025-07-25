package com.dryve.desafio.dto

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class VeiculoDTO(
    val id: UUID,
    val placa: String,
    val precoAnuncio: BigDecimal,
    val ano: Int,
    val precoFipe: BigDecimal,
    val dataCadastro: LocalDateTime,
    val modelo: String,
    val marca: String
)
