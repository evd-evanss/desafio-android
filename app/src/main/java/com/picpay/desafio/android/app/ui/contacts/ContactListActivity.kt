package com.picpay.desafio.android.app.ui.contacts

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.app.base.BaseActivity
import com.picpay.desafio.android.databinding.ActivityContactsBinding
import com.picpay.desafio.android.domain.model.local.UserContact
import com.picpay.desafio.android.utils.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ContactListActivity : BaseActivity<ActivityContactsBinding>() {

    private val viewModel: ContactListViewModel by viewModels()

    @Inject
    lateinit var adapter: ContactListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        setListeners()
        setObservers()
        viewModel.handle(ContactListIntent.GetContacts)
    }

    override fun getViewBinding() = ActivityContactsBinding.inflate(layoutInflater)

    private fun initUi() = binding.run {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this@ContactListActivity)
    }

    private fun setListeners() = binding.run {
        swipeRefresh.setOnRefreshListener {
            viewModel.handle(ContactListIntent.RefreshContacts())
        }
    }

    private fun setObservers() = viewModel.state.observe(this@ContactListActivity) { state ->
        when (state) {
            is ContactListState.LoadContacts -> loadContacts(contacts = state.contacts)
            is ContactListState.LoadSavedContacts -> loadContacts(contacts = state.contacts)
            is ContactListState.DisplayError -> displayError()
            is ContactListState.DisplayRefresh -> displayRefresh(isRefresh = state.isRefresh)
        }
    }

    private fun loadContacts(contacts: List<UserContact>) = binding.run{
        adapter.contacts = contacts
    }

    private fun displayError() = showToast(getString(R.string.error))

    private fun displayRefresh(isRefresh: Boolean) = binding.run {
        swipeRefresh.isRefreshing = isRefresh
    }
}
