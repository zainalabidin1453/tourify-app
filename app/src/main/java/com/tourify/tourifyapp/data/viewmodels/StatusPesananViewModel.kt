package com.tourify.tourifyapp.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tourify.tourifyapp.data.repositories.StatusPesananRepository
import com.tourify.tourifyapp.model.StatusPesananResponse
import com.tourify.tourifyapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class StatusPesananViewModel(private val statusPesananRepository: StatusPesananRepository) :
    ViewModel() {
    private val _uiState: MutableStateFlow<UiState<StatusPesananResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<StatusPesananResponse>> get() = _uiState
    fun statusPesanan(bookingCode: String) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val response = statusPesananRepository.statusPesanan(bookingCode)
                _uiState.value = UiState.Success(response)
            } catch (e: HttpException) {
                val error = e.code()
                _uiState.value = UiState.Error(error = error)
            }
        }
    }
}



