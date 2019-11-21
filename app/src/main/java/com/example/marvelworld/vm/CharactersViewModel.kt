package com.example.marvelworld.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelworld.ext.empty

class CharactersViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text

    private val _isLoadingData = MutableLiveData<Boolean>(false)
    val isLoadingData: LiveData<Boolean> = _isLoadingData

    private val _searchInput = MutableLiveData<String>(String.empty)
    val searchInput: LiveData<String> = _searchInput

    fun setLoadingState(isOrNot: Boolean) {
        _isLoadingData.value = isOrNot
    }
}