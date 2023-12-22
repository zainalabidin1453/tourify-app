package com.tourify.tourifyapp.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tourify.tourifyapp.data.repositories.LoginRepository
import com.tourify.tourifyapp.model.LoginResponse
import com.tourify.tourifyapp.preference.LoginDataStore
import com.tourify.tourifyapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val loginDataStore: LoginDataStore
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<LoginResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<LoginResponse>> get() = _uiState
    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val response = loginRepository.login(email, password)
                _uiState.value = UiState.Success(response)
                loginDataStore.saveLoginStatus(response)
            } catch (e: HttpException) {
                val error = e.code()
                _uiState.value = UiState.Error(error = error)
            }
        }
    }
}



