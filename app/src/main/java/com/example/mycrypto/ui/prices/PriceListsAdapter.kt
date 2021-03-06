package com.example.mycrypto.ui.prices

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mycrypto.R
import com.example.mycrypto.data.models.AssetDataModel
import com.example.mycrypto.data.models.retrofitmodels.AssetDataModelRetrofit
import com.example.mycrypto.data.models.retrofitmodels.AssetsModelRetrofit
import com.example.mycrypto.databinding.AssetsListItemBinding
import com.example.mycrypto.ui.utils.setChangeValue
import com.example.mycrypto.ui.utils.setPriceAmount
import java.text.DecimalFormat

class PricesListAdapter(private val context: Context, private var assetsListItems : ArrayList<AssetsModelRetrofit>) :
    RecyclerView.Adapter<PricesListAdapter.PricesViewHolder>(), Filterable{
    private lateinit var assetsListItemBinding : AssetsListItemBinding
    var filterAssetsListName : ArrayList<AssetsModelRetrofit> = arrayListOf()

    inner class PricesViewHolder(val listItemBinding: AssetsListItemBinding) : RecyclerView.ViewHolder(listItemBinding.root) {
       fun bindData(dataListItem: AssetsModelRetrofit) {
           dataListItem.let {
               listItemBinding.assetsItem = it
               setPriceAmount(listItemBinding.headerPriceTv,DecimalFormat("0.00").format(it.priceUsd?.toDouble()))
               setChangeValue(listItemBinding.headerChangeTv,DecimalFormat("0.00").format(it.changePercent24Hr?.toDouble()))
           }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PricesListAdapter.PricesViewHolder {
        assetsListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.assets_list_item,parent,false)
        return PricesViewHolder(assetsListItemBinding)
    }

    override fun onBindViewHolder(holder: PricesListAdapter.PricesViewHolder, position: Int) {
        //val dataListItem = assetsListItems[holder.adapterPosition]
        val dataListItem = assetsListItems[holder.adapterPosition]
        holder.bindData(dataListItem)
    }

    override fun getItemCount(): Int {
        return assetsListItems.size
    }

    fun updateData(list: ArrayList<AssetsModelRetrofit>) {
        this.assetsListItems = list
        filterAssetsListName = assetsListItems
        notifyDataSetChanged()
    }
/*
    fun updateData(list: ArrayList<AssetDataModel>) {
        this.assetsListItems = list
        filterAssetsListName = assetsListItems
        notifyDataSetChanged()
    }
*/

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val resultList = filterAssetsListName
                val charString = p0.toString()
                val filterdList = ArrayList<AssetsModelRetrofit>()
                if(charString.isEmpty()){
                    filterdList.addAll(filterAssetsListName)
                }else{
                    for (res in resultList){
                        if(res.name?.lowercase()?.contains(charString.lowercase()) == true){
                            filterdList.add(res)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filterdList
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                assetsListItems = p1?.values as ArrayList<AssetsModelRetrofit>
                /*assetsListItems = if(p1?.values == null)
                    ArrayList()
                else
                    p1.values as ArrayList<AssetDataModel>*/
                notifyDataSetChanged()

            }

        }
    }
}