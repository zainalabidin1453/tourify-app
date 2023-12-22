package com.tourify.tourifyapp.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tourify.tourifyapp.data.repositories.RecommendationRepository
import com.tourify.tourifyapp.model.RecommendationResponse
import com.tourify.tourifyapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RecommendationViewModel(private val recommendationRepository: RecommendationRepository) :
    ViewModel() {
    private val _uiState: MutableStateFlow<UiState<RecommendationResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<RecommendationResponse>> get() = _uiState
    fun getMyTickets(userId: Int) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val response = recommendationRepository.getRecommendation(userId)
                _uiState.value = UiState.Success(response)
            } catch (e: HttpException) {
                val error = e.code()
                _uiState.value = UiState.Error(error = error)
            }
        }
    }
}



