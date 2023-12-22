package com.tourify.tourifyapp.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tourify.tourifyapp.data.repositories.FavoriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {
    private val _favoriteItems = MutableStateFlow<List<Int>>(emptyList())
    val favoriteItems: StateFlow<List<Int>> get() = _favoriteItems.asStateFlow()

    fun addToFavorite(userId: Int, destinationId: Int) {
        viewModelScope.launch {
            try {
                favoriteRepository.addFavorites(userId, destinationId)
                val response = favoriteRepository.getFavorites(userId)
                _favoriteItems.value = response.data
            } catch (e: Exception) {
            }
        }
    }

    fun deleteFromFavorite(userId: Int, destinationId: Int) {
        viewModelScope.launch {
            try {
                favoriteRepository.deleteFavorites(userId, destinationId)
                val response = favoriteRepository.getFavorites(userId)
                _favoriteItems.value = response.data
            } catch (_: Exception) {
            }
        }
    }

    fun getFavorites(userId: Int) {
        viewModelScope.launch {
            try {
                val response = favoriteRepository.getFavorites(userId)
                _favoriteItems.value = response.data
            } catch (_: Exception) {
            }
        }
    }
}
