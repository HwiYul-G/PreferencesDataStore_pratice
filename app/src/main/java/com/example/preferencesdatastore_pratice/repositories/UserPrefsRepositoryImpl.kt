package com.example.preferencesdatastore_pratice.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.preferencesdatastore_pratice.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPrefsRepositoryImpl(context: Context) : UserPrefsRepository {

    private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")
    private val dataStore = context.userDataStore

    private companion object {
        val KEY_AGE = intPreferencesKey("age")
        val KEY_NAME = stringPreferencesKey("name")
    }

    override suspend fun saveUser(user: User) {
        dataStore.edit {
            it[KEY_AGE] = user.age
            it[KEY_NAME] = user.name
        }
    }

    override fun getUser(): Flow<User> {
        return dataStore.data
            .catch { e->
                if(e is IOException){
                    emit(emptyPreferences())
                }else{
                    throw e
                }
            }
            .map {
                User(
                    it[KEY_AGE] ?: 0,
                    it[KEY_NAME] ?: ""
                )
            }

    }

    override suspend fun deleteNameUser(){
        dataStore.edit {
            it.remove(KEY_NAME)
        }
    }

    override suspend fun deleteAllUser(){
        dataStore.edit {
            it.clear()
        }
    }

}