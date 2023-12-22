package com.tourify.tourifyapp.data.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tourify.tourifyapp.data.repositories.RecommendationRepository
import com.tourify.tourifyapp.data.viewmodels.RecommendationViewModel

class ViewModelRecommendationFactory(
    private val repository: RecommendationRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecommendationViewModel::class.java)) {
            return RecommendationViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("")
    }
}