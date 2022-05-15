package com.example.mycrypto.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssetsModel(
    @SerialName("data")
    var data : ArrayList<AssetDataModel> = arrayListOf(),
    @SerialName("timestamp")
    var timestamp : Long? = null

)
