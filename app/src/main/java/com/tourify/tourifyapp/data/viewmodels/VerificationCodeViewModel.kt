package com.tourify.tourifyapp.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tourify.tourifyapp.data.repositories.VerificationCodeRepository
import com.tourify.tourifyapp.model.VerificationResponse
import com.tourify.tourifyapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class VerificationCodeViewModel(private val verificationCodeRepository: VerificationCodeRepository) :
    ViewModel() {
    private val _uiState: MutableStateFlow<UiState<VerificationResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<VerificationResponse>> get() = _uiState
    fun verificationCode(email: String, codeVerif: String) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val response = verificationCodeRepository.verificationCode(email, codeVerif)
                _uiState.value = UiState.Success(response)
            } catch (e: HttpException) {
                val error = e.code()
                _uiState.value = UiState.Error(error = error)
            }
        }
    }
}



