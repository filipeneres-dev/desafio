import java.math.BigDecimal

data class VeiculoRequest(
    val placa: String,
    val idMarca: Int,
    val idModelo: Int,
    val precoAnuncio: BigDecimal,
    val ano: Int
)
