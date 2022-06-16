package com.example.mycrypto.ui.favorites

import android.os.Bundle
import android.view.View
import com.example.mycrypto.R
import com.example.mycrypto.ui.base.BaseFragment

class FavoritesFragment : BaseFragment(R.layout.fragment_favorites) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolBar(title = R.string.menu_title_2)
    }
}