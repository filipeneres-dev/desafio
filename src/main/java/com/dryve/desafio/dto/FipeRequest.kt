package com.dryve.desafio.dto

data class FipeRequest(
    val codigoTabelaReferencia: Int,
    val codigoTipoVeiculo: Int,
    val codigoMarca: Int,
    val codigoTipoCombustivel: Int,
    val anoModelo: Int,
    val codigoModelo: Int,
    val tipoConsulta: String = "tradicional"
)