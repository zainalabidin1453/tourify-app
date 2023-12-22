package com.tourify.tourifyapp.data.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tourify.tourifyapp.data.repositories.VerificationCodeRepository
import com.tourify.tourifyapp.data.viewmodels.VerificationCodeViewModel

class ViewModelVerificationCodeFactory(
    private val repository: VerificationCodeRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VerificationCodeViewModel::class.java)) {
            return VerificationCodeViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("")
    }
}