package com.example.mycrypto.ui.prices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycrypto.data.models.AssetDataModel
import com.example.mycrypto.data.models.AssetsModel
import com.example.mycrypto.data.services.BaseResponse
import com.example.mycrypto.data.services.RepositorySDK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PricesViewModel @Inject constructor(
    private val repositorySDK: RepositorySDK
    ) : ViewModel() {
    private val _assetsData = MutableLiveData<BaseResponse<AssetsModel>>()
    val assetsData : LiveData<BaseResponse<AssetsModel>>
    get() = _assetsData
    init {
        getCryptoAssetsData()
    }

    fun getCryptoAssetsData() {
        viewModelScope.launch {
            _assetsData.postValue(BaseResponse.Loading())
            kotlin.runCatching {
                repositorySDK.getAssetsData()
            }.onSuccess {
                _assetsData.postValue(BaseResponse.Success(it))
            }.onFailure {
                _assetsData.postValue(BaseResponse.Error("Something went wrong"))
            }
        }
    }
}