package com.tourify.tourifyapp.data.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tourify.tourifyapp.data.repositories.CheckEmailRepository
import com.tourify.tourifyapp.data.viewmodels.CheckEmailViewModel

class ViewModelCheckEmailFactory(
    private val repository: CheckEmailRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckEmailViewModel::class.java)) {
            return CheckEmailViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("")
    }
}