package com.dryve.desafio.client


import com.dryve.desafio.dto.FipeRequest
import com.dryve.desafio.dto.FipeResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "fipeClient",
    url = "http://veiculos.fipe.org.br/api/veiculos"
)
interface FipeClient {

    @PostMapping(
        "/ConsultarValorComTodosParametros",
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun consultarValor(@RequestBody request: FipeRequest): FipeResponse
}
