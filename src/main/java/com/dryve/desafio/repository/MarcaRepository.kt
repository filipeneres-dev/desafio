package com.dryve.desafio.repository

import com.dryve.desafio.dto.MarcaComTotalModelosDTO
import com.dryve.desafio.domain.Marca
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface MarcaRepository : CrudRepository<Marca, UUID> {

    @Query(
        """
        SELECT new com.dryve.desafio.dto.MarcaComTotalModelosDTO(m.id, m.nome, COUNT(mod)) 
        FROM Marca m 
        LEFT JOIN Modelo mod ON mod.marca.id = m.id 
        GROUP BY m.id, m.nome
        """
    )
    fun listarMarcasComTotalModelos(): List<MarcaComTotalModelosDTO>

    fun findFirstByNomeIgnoreCase(nome: String): Marca?
}
