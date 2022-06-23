package com.example.mycrypto.ui.prices

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycrypto.R
import com.example.mycrypto.data.models.AssetDataModel
import com.example.mycrypto.data.models.retrofitmodels.AssetsModelRetrofit
import com.example.mycrypto.data.services.BaseResponse
import com.example.mycrypto.databinding.FragmentPricesBinding
import com.example.mycrypto.ui.base.BaseFragment
import com.example.mycrypto.ui.utils.hide
import com.example.mycrypto.ui.utils.setAmountWithCurrencyFormat
import com.example.mycrypto.ui.utils.show
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class PricesFragment : BaseFragment(R.layout.fragment_prices){

    private lateinit var fragmentPricesBinding: FragmentPricesBinding
    private val pricesViewModel by viewModels<PricesViewModel>()
    private val pricesListAdapter:PricesListAdapter by lazy {
        PricesListAdapter(
            requireContext(),
            arrayListOf()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentPricesBinding = FragmentPricesBinding.bind(view)
        initializeViews()
        setObservers()
        onSwipeRefresh()

        fragmentPricesBinding.searchViewEt.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //beforeTextChanged
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //pricesListAdapter.filter.filter(p0)
            }

            override fun afterTextChanged(p0: Editable?) {
                pricesListAdapter.filter.filter(p0.toString())
            }

        })

       /* fragmentPricesBinding.itemSearchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                //pricesListAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                pricesListAdapter.filter.filter(newText)
                return false
            }

        })*/

    }

    private fun onSwipeRefresh() {
        fragmentPricesBinding.swipeRefreshLayoutTag.setOnRefreshListener {
            if(fragmentPricesBinding.searchViewEt.text?.isNotBlank() == true){
                fragmentPricesBinding.searchViewEt.text = null
                fragmentPricesBinding.searchViewEt.clearFocus()
            }
           // pricesViewModel.getCryptoAssetsData()
            pricesViewModel.getCryptoAssestsDataRetrofit()
            fragmentPricesBinding.swipeRefreshLayoutTag.isRefreshing = false
        }
    }

    private fun initializeViews() {
        val llm = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        fragmentPricesBinding.assetsListRv.layoutManager = llm
        fragmentPricesBinding.assetsListRv.adapter = pricesListAdapter
        val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        fragmentPricesBinding.assetsListRv.addItemDecoration(dividerItemDecoration)
    }

    private fun setObservers() {
        /*pricesViewModel.assetsData.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading -> {
                    fragmentPricesBinding.pricesPb.show()
                }
                is BaseResponse.Success -> {
                    fragmentPricesBinding.pricesPb.hide()
                    //if(fragmentPricesBinding.swipeRefreshLayoutTag.isRefreshing) fragmentPricesBinding.swipeRefreshLayoutTag.isRefreshing = false
                    it.data?.let { res ->
                        pricesListAdapter.updateData(res.data)
                        updateGlobalMarketCap(res.data)
                    }?: kotlin.run {
                        Toast.makeText(context,"update error",Toast.LENGTH_SHORT).show()
                    }
                }
                is BaseResponse.Error -> {
                    fragmentPricesBinding.pricesPb.hide()
                    Toast.makeText(context,"error",Toast.LENGTH_SHORT).show()
                }
            }
        }*/

        pricesViewModel.assetsList.observe(viewLifecycleOwner) {
            it?.let { res ->
                pricesListAdapter.updateData(res.data)
                updateGlobalMarketCap(res.data)
            } ?: kotlin.run {
                Toast.makeText(context, "update error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateGlobalMarketCap(data: ArrayList<AssetsModelRetrofit>) {
        var globalMarketCapData = 0.0
        for(price in data){
            price.priceUsd?.let {
                globalMarketCapData +=it.toDouble()
            }
        }
        fragmentPricesBinding.marketCapTv.text = getString(R.string.global_market_cap_str,setAmountWithCurrencyFormat(globalMarketCapData))
    }

}