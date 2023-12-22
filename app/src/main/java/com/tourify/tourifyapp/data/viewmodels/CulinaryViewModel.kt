package com.tourify.tourifyapp.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tourify.tourifyapp.data.repositories.CulinaryRepository
import com.tourify.tourifyapp.model.CulinaryResponse
import com.tourify.tourifyapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CulinaryViewModel(
    private val culinaryRepository: CulinaryRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CulinaryResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CulinaryResponse>> get() = _uiState
    fun getCulinary() {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val response = culinaryRepository.getCulinary()
                _uiState.value = UiState.Success(response)
            } catch (e: HttpException) {
                val error = e.code()
                _uiState.value = UiState.Error(error = error)
            }
        }
    }
}



