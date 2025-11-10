package com.example.lab_week_10.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab_week_10.database.Total
import com.example.lab_week_10.database.TotalDao

class TotalViewModel(private val totalDao: TotalDao): ViewModel() {
    //Declare the LiveData object
    private val _total = MutableLiveData<Int>()
    val total: LiveData<Int> = _total
    //Initialize the LiveData object
    init {
        //postValue is used to set the value of the LiveData object
        //from a background thread or the main thread
        //While on the other hand setValue() is used
        //only if you're on the main thread
        _total.postValue(0)
    }
    //Increment the total value
    fun incrementTotal() {
        val newTotal = (_total.value ?: 0) + 1
        _total.postValue(newTotal)
        totalDao.update(Total(id = 1, total = newTotal))
    }

    //Set new total value
    fun setTotal(newTotal: Int) {
        _total.postValue(newTotal)
    }
}