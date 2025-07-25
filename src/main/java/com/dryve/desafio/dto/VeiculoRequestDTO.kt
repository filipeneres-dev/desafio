package com.dryve.desafio.dto

import java.math.BigDecimal

data class VeiculoRequestDTO(
    val placa: String,
    val idMarca: Int,
    val idModelo: Int,
    val precoAnuncio: BigDecimal,
    val ano: Int
)
