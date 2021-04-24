package com.picpay.desafio.android.app.ui.userlist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.app.base.BaseActivity
import com.picpay.desafio.android.databinding.ActivityUsersBinding
import com.picpay.desafio.android.domain.model.local.User
import com.picpay.desafio.android.utils.extensions.setVisible
import com.picpay.desafio.android.utils.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserListActivity : BaseActivity<ActivityUsersBinding>() {

    private val viewModel: UserListViewModel by viewModels()

    @Inject
    lateinit var adapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        setListeners()
        setObservers()
        viewModel.handle(UserListIntent.GetUsers)
    }

    override fun getViewBinding() = ActivityUsersBinding.inflate(layoutInflater)

    private fun initUi() = binding.run {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this@UserListActivity)
    }

    private fun setListeners() = binding.run {
        swipeRefresh.setOnRefreshListener {
            viewModel.handle(UserListIntent.Refresh())
        }
    }

    private fun setObservers() = viewModel.state.observe(this@UserListActivity) { state ->
        when (state) {
            is UserListState.Loading -> displayLoading(isLoading = state.isLoading)
            is UserListState.LoadUsers -> loadUsers(users = state.users)
            is UserListState.DisplayError -> displayError()
            is UserListState.SetRefresh -> setRefresh(isRefresh = state.isRefresh)
        }
    }

    private fun displayLoading(isLoading: Boolean) = binding.run {
        userListProgressBar.setVisible(visible = isLoading)
    }

    private fun loadUsers(users: List<User>) = binding.run{
        adapter.users = users
    }

    private fun displayError() = showToast(getString(R.string.error))

    private fun setRefresh(isRefresh: Boolean) = binding.run {
        swipeRefresh.isRefreshing = isRefresh
    }
}
