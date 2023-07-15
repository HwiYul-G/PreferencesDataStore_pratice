package com.example.preferencesdatastore_pratice.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.preferencesdatastore_pratice.models.User
import com.example.preferencesdatastore_pratice.repositories.UserPrefsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// TODO : application : Application이 아니라 임시로, context : Context를 넣어준다.
class MainActivityViewModel(context : Context) : ViewModel(){

    // datastore
    private val userPrefsRepository = UserPrefsRepositoryImpl(context)


    val getUser = userPrefsRepository.getUser().asLiveData(Dispatchers.IO)

    fun saveUser(user : User){
        viewModelScope.launch {
            userPrefsRepository.saveUser(user)
        }
    }


}