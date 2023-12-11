package com.tourify.tourifyapp.data.viewmodels

import androidx.lifecycle.ViewModel
import com.tourify.tourifyapp.data.repositories.CheckEmailRepository
import com.tourify.tourifyapp.model.RegistrationResponse
import com.tourify.tourifyapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CheckEmailViewModel(private val checkEmailRepository: CheckEmailRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<RegistrationResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<RegistrationResponse>> get() = _uiState
    
}



