package com.dryve.desafio.domain

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
data class Veiculo(
    @Id
    @GeneratedValue
    val id: UUID? = null,

    val placa: String = "",

    @Column(name = "preco_anuncio")
    val precoAnuncio: BigDecimal = BigDecimal.ZERO,

    val ano: Int = 0,

    @Column(name = "preco_fipe")
    val precoFipe: BigDecimal = BigDecimal.ZERO,

    @Column(name = "data_cadastro")
    val dataCadastro: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "modelo_id")
    val modelo: Modelo = Modelo()
)
