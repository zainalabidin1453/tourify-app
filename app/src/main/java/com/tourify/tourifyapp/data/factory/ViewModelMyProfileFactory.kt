package com.tourify.tourifyapp.data.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tourify.tourifyapp.data.repositories.MyProfileRepository
import com.tourify.tourifyapp.data.viewmodels.MyProfileViewModel

class ViewModelMyProfileFactory(
    private val repository: MyProfileRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyProfileViewModel::class.java)) {
            return MyProfileViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("")
    }
}