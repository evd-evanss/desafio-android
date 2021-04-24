package com.picpay.desafio.android.app.ui.userlist

sealed class UserListIntent {
    data class Refresh(val isRefresh: Boolean = true) : UserListIntent()
    object GetUsers : UserListIntent()
}
