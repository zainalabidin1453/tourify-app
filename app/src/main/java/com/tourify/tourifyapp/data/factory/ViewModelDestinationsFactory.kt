package com.tourify.tourifyapp.data.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tourify.tourifyapp.data.repositories.DestinationsRepository
import com.tourify.tourifyapp.data.viewmodels.DestinationsViewModel

class ViewModelDestinationsFactory(
    private val repository: DestinationsRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DestinationsViewModel::class.java)) {
            return DestinationsViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("")
    }
}