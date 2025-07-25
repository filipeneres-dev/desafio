package com.dryve.desafio.repository

import com.dryve.desafio.domain.Modelo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ModeloRepository : JpaRepository<Modelo, Long> {
    fun findFirstByNomeIgnoreCase(nome: String): Modelo?
}