package com.tourify.tourifyapp.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tourify.tourifyapp.data.repositories.CheckEmailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CheckEmailViewModel(private val checkEmailRepository: CheckEmailRepository) : ViewModel() {

}



