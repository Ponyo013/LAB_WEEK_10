package com.example.lab_week_10.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lab_week_10.database.TotalDao

class TotalViewModelFactory(private val totalDao: TotalDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TotalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TotalViewModel(totalDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}