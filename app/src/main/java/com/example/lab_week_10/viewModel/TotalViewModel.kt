package com.example.lab_week_10.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TotalViewModel: ViewModel() {
    // LiveData untuk diamati dari Activity/Fragment
    private val _total = MutableLiveData(0)
    val total: LiveData<Int> = _total

    fun incrementTotal() {
        _total.value = (_total.value ?: 0) + 1
    }
}