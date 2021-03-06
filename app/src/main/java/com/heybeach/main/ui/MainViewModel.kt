package com.heybeach.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.heybeach.main.data.MainModel

class MainViewModel(private val data: MainModel) : ViewModel() {

    private val _mainModel = MutableLiveData<MainModel>()
    val mainModel: LiveData<MainModel>
        get() = _mainModel

    init {
        _mainModel.value = data
    }

    fun setSelectedFragment(fragmentName: String) {
        if (fragmentName != data.selectedFragmentName) {
            data.selectedFragmentName = fragmentName
            _mainModel.value = data
        }
    }

}