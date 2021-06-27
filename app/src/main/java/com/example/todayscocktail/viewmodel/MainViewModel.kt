package com.example.todayscocktail.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todayscocktail.data.DrinksListRemoteEntity
import com.example.todayscocktail.data.network.CocktailService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainViewModel: ViewModel() {
    val listDrinks = MutableLiveData<DrinksListRemoteEntity>()

    fun getDrinks() {
        viewModelScope.launch {
            try {
                listDrinks.value = getDrinksList()
            } catch (e: Exception) {
                Log.e("MainViewModel", e.toString())
            }
        }
    }

    private suspend fun getDrinksList(): DrinksListRemoteEntity {
        return withContext(Dispatchers.IO) {
            CocktailService.service.getDrinks()
        }
    }
}