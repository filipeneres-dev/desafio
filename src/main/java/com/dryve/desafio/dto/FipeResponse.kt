package com.dryve.desafio.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class FipeResponse(
    @JsonProperty("Valor")
    val valor: String? = null,

    @JsonProperty("Marca")
    val marca: String? = null,

    @JsonProperty("Modelo")
    val modelo: String? = null,

    @JsonProperty("AnoModelo")
    val anoModelo: Int? = null,

    @JsonProperty("Combustivel")
    val combustivel: String? = null,

    @JsonProperty("CodigoFipe")
    val codigoFipe: String? = null,

    @JsonProperty("MesReferencia")
    val mesReferencia: String? = null,

    @JsonProperty("Autenticacao")
    val autenticacao: String? = null,

    @JsonProperty("TipoVeiculo")
    val tipoVeiculo: Int? = null,

    @JsonProperty("SiglaCombustivel")
    val siglaCombustivel: String? = null,

    @JsonProperty("DataConsulta")
    val dataConsulta: String? = null,

    @JsonProperty("codigo")
    val codigo: String? = null,

    @JsonProperty("erro")
    val erro: String? = null
)
