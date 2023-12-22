package com.tourify.tourifyapp.data.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tourify.tourifyapp.data.repositories.CreatePasswordRepository
import com.tourify.tourifyapp.data.viewmodels.CreatePasswordViewModel
import com.tourify.tourifyapp.preference.LoginDataStore

class ViewModelCreatePasswordFactory(
    private val repository: CreatePasswordRepository,
    private val loginDataStore: LoginDataStore
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreatePasswordViewModel::class.java)) {
            return CreatePasswordViewModel(repository, loginDataStore) as T
        }
        throw java.lang.IllegalArgumentException("")
    }
}