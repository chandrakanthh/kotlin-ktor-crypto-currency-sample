package com.example.mycrypto.ui.prices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycrypto.data.models.AssetDataModel
import com.example.mycrypto.data.models.AssetsModel
import com.example.mycrypto.data.models.retrofitmodels.AssetDataModelRetrofit
import com.example.mycrypto.data.models.retrofitmodels.AssetsModelRetrofit
import com.example.mycrypto.data.services.BaseResponse
import com.example.mycrypto.data.services.RepositorySDK
import com.example.mycrypto.data.services.retrofit.RetroRepos
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.security.auth.callback.Callback

@HiltViewModel
class PricesViewModel @Inject constructor(
    private val repositorySDK: RepositorySDK,
    private val retroRepos: RetroRepos
    ) : ViewModel() {
    private val _assetsData = MutableLiveData<BaseResponse<AssetsModel>>()
    val assetsData : LiveData<BaseResponse<AssetsModel>>
    get() = _assetsData

    //retrofit calls
    val assetsList  = MutableLiveData<AssetDataModelRetrofit>()
    val assetsListError  = MutableLiveData<String>()

    init {
        //getCryptoAssetsData()
        getCryptoAssestsDataRetrofit()
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

    fun getCryptoAssestsDataRetrofit() {
        retroRepos.getAssetsApiCallResponse().enqueue(object :
            retrofit2.Callback<AssetDataModelRetrofit> {
            override fun onResponse(
                call: Call<AssetDataModelRetrofit>,
                response: Response<AssetDataModelRetrofit>
            ) {
                response.let {
                    assetsList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<AssetDataModelRetrofit>, t: Throwable) {
                assetsListError.postValue(t.message)
            }

        })

    }
}