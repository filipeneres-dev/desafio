package com.dryve.desafio.repository

import com.dryve.desafio.domain.Veiculo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.UUID

@Repository
interface VeiculoRepository : JpaRepository<Veiculo, Long> {
    fun findByPlacaIgnoreCase(placa: String): Veiculo?

    fun findAllByModelo_Marca_Id(idMarca: UUID, pageable: Pageable): Page<Veiculo>
}