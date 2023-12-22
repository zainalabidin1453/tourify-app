package com.tourify.tourifyapp.data.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tourify.tourifyapp.data.repositories.ScanningObjectRepository
import com.tourify.tourifyapp.data.viewmodels.ScanningObjectViewModel

class ViewModelScanningObjectFactory(
    private val repository: ScanningObjectRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScanningObjectViewModel::class.java)) {
            return ScanningObjectViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("")
    }
}