package com.dryve.desafio.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "modelo")
data class Modelo(
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    var id: UUID? = null,

    @Column(name = "nome", nullable = false)
    var nome: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id", nullable = false)
    var marca: Marca = Marca()
)
