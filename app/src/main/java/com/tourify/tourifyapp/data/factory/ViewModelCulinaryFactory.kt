package com.tourify.tourifyapp.data.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tourify.tourifyapp.data.repositories.CulinaryRepository
import com.tourify.tourifyapp.data.viewmodels.CulinaryViewModel

class ViewModelCulinaryFactory(
    private val repository: CulinaryRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CulinaryViewModel::class.java)) {
            return CulinaryViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("")
    }
}