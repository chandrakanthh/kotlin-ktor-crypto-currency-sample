package com.example.mycrypto.ui.base

import androidx.fragment.app.Fragment

abstract class BaseFragment(layoutRes:Int): Fragment(layoutRes) {
    fun setUpToolBar(title: Int){
        getMainActivity().activityMainBinding.toolbar.title = getString(title)
    }

    private fun getMainActivity(): MainActivity{
        return requireActivity() as MainActivity
    }

}