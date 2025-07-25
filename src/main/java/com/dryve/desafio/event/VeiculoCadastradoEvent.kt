package com.dryve.desafio.event

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class VeiculoCadastradoEvent(
    val id: UUID,
    val placa: String,
    val precoAnuncio: BigDecimal,
    val precoFipe: BigDecimal,
    val ano: Int,
    val dataCadastro: LocalDateTime,
    val modeloId: UUID
)
