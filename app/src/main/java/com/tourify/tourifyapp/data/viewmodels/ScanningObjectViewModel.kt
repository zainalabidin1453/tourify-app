package com.tourify.tourifyapp.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tourify.tourifyapp.data.repositories.ScanningObjectRepository
import com.tourify.tourifyapp.model.ScanningObjectResponse
import com.tourify.tourifyapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.HttpException

class ScanningObjectViewModel(private val scanningObjectRepository: ScanningObjectRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<ScanningObjectResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<ScanningObjectResponse>> get() = _uiState
    fun scanningObject(image: MultipartBody.Part) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val response = scanningObjectRepository.scanningObject(image)
                _uiState.value = UiState.Success(response)
            } catch (e: HttpException) {
                val error = e.code()
                _uiState.value = UiState.Error(error = error)
            }
        }
    }
}



