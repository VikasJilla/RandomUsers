package com.example.randomusers.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.randomusers.data.models.ResultSuccess
import com.example.randomusers.data.repositories.UsersRepository
import com.example.randomusers.presentation.states.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsersViewModel(private val usersRepository: UsersRepository) : ViewModel() {
    private var state: MutableStateFlow<UIState> = MutableStateFlow(InitialUIState())

    val uiState: StateFlow<UIState> = state.asStateFlow()

    private var page:Int = 1

    private val tag = "UsersViewModel"

    fun loadUsers(){
        Log.d(tag,"in LoadUsers")
        state.update {
            ProgressState()
        }
        fetchUsers()
    }

    fun loadMoreUsers(){
        page++
        state.update {
            LoadingMoreState()
        }
        fetchUsers()
    }

    private fun fetchUsers(){
        Log.d(tag,"in fetchUsers")
        viewModelScope.launch {
            try{
                val result = usersRepository.fetchUsers(page)
                Log.d(tag,"in fetchUsers result $result ${result.data}")
                state.update {
                    ResultUIState(result is ResultSuccess<*>, result.data)
                }
            }catch (e:Exception){
                Log.d(tag,"in fetchUsers exception $e")
                state.update {
                    ResultUIState(false,e.localizedMessage)
                }
            }
        }
    }
}

class UsersViewModelFactory(private val repository: UsersRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsersViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}