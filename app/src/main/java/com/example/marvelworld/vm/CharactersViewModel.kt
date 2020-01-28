package com.example.marvelworld.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CharactersViewModel : ViewModel() {

    private val _isLoadingData = MutableLiveData<Boolean>(false)
    val isLoadingData: LiveData<Boolean> = _isLoadingData
    private val _searchBarVisibility = MutableLiveData<Boolean>(false)
    val searchBarVisibility: LiveData<Boolean> = _searchBarVisibility

    fun setLoadingState(isOrNot: Boolean) {
        _isLoadingData.value = isOrNot
    }

    fun showSearchBar(show: Boolean) {
        _searchBarVisibility.value = show
    }
}