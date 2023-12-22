package com.tourify.tourifyapp.data.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tourify.tourifyapp.data.repositories.LoginRepository
import com.tourify.tourifyapp.data.viewmodels.LoginViewModel
import com.tourify.tourifyapp.preference.LoginDataStore

class ViewModelLoginFactory(
    private val repository: LoginRepository,
    private val loginDataStore: LoginDataStore
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository, loginDataStore) as T
        }
        throw java.lang.IllegalArgumentException("")
    }
}