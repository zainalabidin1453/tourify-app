package com.tourify.tourifyapp.data.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tourify.tourifyapp.data.repositories.StatusPesananRepository
import com.tourify.tourifyapp.data.viewmodels.StatusPesananViewModel

class ViewModelStatusPesananFactory(
    private val repository: StatusPesananRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatusPesananViewModel::class.java)) {
            return StatusPesananViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("")
    }
}