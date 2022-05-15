package com.example.mycrypto.ui.utils


import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.mycrypto.R
import java.text.NumberFormat
import java.util.*


@BindingAdapter("priceUsd")
fun setPriceAmount(view:AppCompatTextView,price:String?){
    view.text = view.context.getString(R.string.dollar_symbol,
        setAmountWithCurrencyFormat(price?.toDouble()?:0.0))
}

@BindingAdapter("changePercent24Hr")
fun setChangeValue(view: AppCompatTextView, changeValue : String?){
    view.text = view.context.getString(R.string.change_percentile,changeValue)
}

fun setAmountWithCurrencyFormat( globalMarketCapData: Double?):String {
    return NumberFormat.getInstance(Locale.US).format(globalMarketCapData)
}

fun View.hide(){
    isVisible = false
}

fun View.show(){
    isVisible = true
}