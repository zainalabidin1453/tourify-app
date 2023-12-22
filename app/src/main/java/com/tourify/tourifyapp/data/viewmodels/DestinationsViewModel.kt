package com.tourify.tourifyapp.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tourify.tourifyapp.data.repositories.DestinationsRepository
import com.tourify.tourifyapp.model.DestinationsResponse
import com.tourify.tourifyapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DestinationsViewModel(
    private val destinationsRepository: DestinationsRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<DestinationsResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<DestinationsResponse>> get() = _uiState
    fun getDestinations() {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val response = destinationsRepository.getDestinations()
                _uiState.value = UiState.Success(response)
            } catch (e: HttpException) {
                val error = e.code()
                _uiState.value = UiState.Error(error = error)
            }
        }
    }
}



