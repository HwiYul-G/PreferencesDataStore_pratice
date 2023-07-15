package com.example.preferencesdatastore_pratice.repositories

import com.example.preferencesdatastore_pratice.models.User
import kotlinx.coroutines.flow.Flow

interface UserPrefsRepository {

    suspend fun saveUser(user : User)

    fun getUser() : Flow<User>

    suspend fun deleteNameUser()

    suspend fun deleteAllUser()
}