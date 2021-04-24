package com.picpay.desafio.android.app.ui.userlist

import com.picpay.desafio.android.domain.model.local.User

sealed class UserListState {
    object DisplayError : UserListState()
    data class Loading(val isLoading: Boolean) : UserListState()
    data class LoadUsers(val users: List<User>) : UserListState()
    data class SetRefresh(val isRefresh: Boolean) : UserListState()
}
