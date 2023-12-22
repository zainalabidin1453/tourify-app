package com.tourify.tourifyapp.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tourify.tourifyapp.data.repositories.MyProfileRepository
import com.tourify.tourifyapp.model.MyProfileResponse
import com.tourify.tourifyapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MyProfileViewModel(private val myProfileRepository: MyProfileRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<MyProfileResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<MyProfileResponse>> get() = _uiState
    fun getMyProfile(userId: Int) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val response = myProfileRepository.getMyProfile(userId)
                _uiState.value = UiState.Success(response)
            } catch (e: HttpException) {
                val error = e.code()
                _uiState.value = UiState.Error(error = error)
            }
        }
    }
}



