package com.example.mycrypto.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssetDataModel(
    @SerialName("id")
    var id: String? = null,
    @SerialName("rank")
    var rank: String? = null,
    @SerialName("symbol")
    var symbol: String? = null,
    @SerialName("name")
    var name: String? = null,
    @SerialName("supply")
    var supply: String? = null,
    @SerialName("maxSupply")
    var maxSupply: String? = null,
    @SerialName("marketCapUsd")
    var marketCapUsd: String? = null,
    @SerialName("volumeUsd24Hr")
    var volumeUsd24Hr: String? = null,
    @SerialName("priceUsd")
    var priceUsd: String? = null,
    @SerialName("changePercent24Hr")
    var changePercent24Hr: String? = null,
    @SerialName("vwap24Hr")
    var vwap24Hr: String? = null,
    @SerialName("explorer")
    var explorer: String? = null
)
