package com.dryve.desafio.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "marca")
data class Marca(
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    var id: UUID? = null,

    @Column(name = "nome", nullable = false)
    var nome: String = ""
)
