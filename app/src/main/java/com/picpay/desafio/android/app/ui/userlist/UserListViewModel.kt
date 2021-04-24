package com.picpay.desafio.android.app.ui.userlist

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.app.base.BaseViewModel
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.domain.model.local.User
import com.picpay.desafio.android.utils.extensions.startCollect
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userRepository: UserRepository
): BaseViewModel<UserListIntent, UserListState> () {

    internal var users = mutableListOf<User>()

    override fun handle(intent: UserListIntent) {
        when (intent) {
            is UserListIntent.GetUsers -> getUsers()
            is UserListIntent.Refresh -> getUsers(isRefresh = intent.isRefresh)
        }
    }

    private fun getUsers(isRefresh: Boolean = false) = viewModelScope.run {
        when {
            isRefresh -> fetchUsersInApi()
            users.isEmpty() -> fetchUsersInApi()
            else -> _state.value = UserListState.LoadUsers(users = users)
        }
    }

    private fun fetchUsersInApi() = userRepository.fetchUsers().startCollect(
        coroutineScope = viewModelScope,
        onLoading = { isLoading ->
            _state.value = UserListState.Loading(isLoading = isLoading)
        },
        onSuccess = { userList ->
            users = userList.toMutableList()
            _state.value = UserListState.LoadUsers(users = users)
            _state.value = UserListState.SetRefresh(isRefresh = false)
        },
        onError = { error ->
            Log.d(javaClass.name, error.message ?: "")
            _state.value = UserListState.DisplayError
            _state.value = UserListState.SetRefresh(isRefresh = false)
        }
    )
}