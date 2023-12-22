package com.tourify.tourifyapp.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tourify.tourifyapp.data.repositories.BookingRepository
import com.tourify.tourifyapp.model.BookingTripResponse
import com.tourify.tourifyapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class BookingViewModel(private val bookingRepository: BookingRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<BookingTripResponse>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<BookingTripResponse>> get() = _uiState
    fun bookingTrip(
        userId: String,
        destinationId: String,
        tourGuideId: String,
        name: String,
        email: String,
        telephone: String,
        tripDate: String,
        note: String
    ) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val response = bookingRepository.bookingTrip(
                    userId,
                    destinationId,
                    tourGuideId,
                    name,
                    email,
                    telephone,
                    tripDate,
                    note
                )
                _uiState.value = UiState.Success(response)
            } catch (e: HttpException) {
                val error = e.code()
                _uiState.value = UiState.Error(error = error)
            }
        }
    }
}



