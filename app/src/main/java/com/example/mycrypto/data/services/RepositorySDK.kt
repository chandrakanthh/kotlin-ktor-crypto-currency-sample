package com.example.mycrypto.data.services

import com.example.mycrypto.data.models.AssetsModel
import com.example.mycrypto.ui.utils.Constants
import io.ktor.client.request.*
import io.ktor.http.*
import kotlin.jvm.Throws

class RepositorySDK(private val remoteService: RemoteService) {

    @Throws(Exception::class)
    suspend fun getAssetsData():AssetsModel {
        return remoteService.httpClient.get {
            url(remoteService.getUrl(Constants.kAssets))
            headers {
                remoteService.addHeaders(this)
            }
        }
    }
}