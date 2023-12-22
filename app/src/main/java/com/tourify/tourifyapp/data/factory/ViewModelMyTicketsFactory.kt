package com.tourify.tourifyapp.data.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tourify.tourifyapp.data.repositories.MyTicketsRepository
import com.tourify.tourifyapp.data.viewmodels.MyTicketsViewModel

class ViewModelMyTicketsFactory(
    private val repository: MyTicketsRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyTicketsViewModel::class.java)) {
            return MyTicketsViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("")
    }
}