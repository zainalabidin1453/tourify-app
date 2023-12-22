package com.tourify.tourifyapp.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tourify.tourifyapp.data.repositories.CheckEmailRepository
import com.tourify.tourifyapp.model.RegistrationResponse
import com.tourify.tourifyapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CheckEmailViewModel(private val checkEmailRepository: CheckEmailRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<RegistrationResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<RegistrationResponse>> get() = _uiState
    fun checkEmail(email: String) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val response = checkEmailRepository.checkEmail(email)
                _uiState.value = UiState.Success(response)
            } catch (e: HttpException) {
                val error = e.code()
                _uiState.value = UiState.Error(error = error)
            }
        }
    }
}



