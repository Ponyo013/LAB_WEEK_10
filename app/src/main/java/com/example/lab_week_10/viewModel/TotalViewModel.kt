package com.example.lab_week_10.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab_week_10.database.Total
import com.example.lab_week_10.database.TotalDao
import com.example.lab_week_10.database.TotalObject
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class TotalViewModel(private val totalDao: TotalDao) : ViewModel() {
    private val _total = MutableLiveData<Int>()
    val total: LiveData<Int> = _total

    var lastUpdatedTimestamp: String? = null  // tambahkan ini

    init {
        _total.postValue(0)
    }

    fun incrementTotal(): String {
        val newValue = (_total.value ?: 0) + 1
        _total.postValue(newValue)

        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formattedDate = currentDateTime.format(formatter)

        // update database
        totalDao.update(
            Total(
                id = 1,
                total = TotalObject(value = newValue, date = formattedDate)
            )
        )

        lastUpdatedTimestamp = formattedDate  // simpan timestamp terakhir
        return formattedDate
    }

    fun setTotal(newTotal: Int) {
        _total.postValue(newTotal)
    }
}
