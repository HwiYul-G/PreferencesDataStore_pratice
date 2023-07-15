package com.example.preferencesdatastore_pratice.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.preferencesdatastore_pratice.models.User
import com.example.preferencesdatastore_pratice.repositories.UserPrefsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivityViewModel(application : Application) : AndroidViewModel(application){


    private val userPrefsRepository = UserPrefsRepositoryImpl(application)


    val getUser = userPrefsRepository.getUser().asLiveData(Dispatchers.IO)

    fun saveUser(user : User){
        viewModelScope.launch {
            userPrefsRepository.saveUser(user)
        }
    }


}