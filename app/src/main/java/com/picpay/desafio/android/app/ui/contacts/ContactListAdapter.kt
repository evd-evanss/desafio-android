package com.picpay.desafio.android.app.ui.contacts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ItemContactBinding
import com.picpay.desafio.android.domain.model.local.UserContact
import com.picpay.desafio.android.utils.extensions.setVisible
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ContactListAdapter @Inject constructor(
    @ApplicationContext
    val context: Context
) : RecyclerView.Adapter<ContactListAdapter.UserListItemViewHolder>() {

    var contacts: List<UserContact> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserListItemViewHolder {
        return UserListItemViewHolder(
            ItemContactBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            ).root
        )
    }

    override fun onBindViewHolder(
        holder: UserListItemViewHolder,
        position: Int
    ) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int = contacts.size

    class UserListItemViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(contacts: UserContact) = ItemContactBinding.bind(itemView).run {
            name.text = contacts.name
            username.text = contacts.username
            progressBar.setVisible(true)
            Picasso.get()
                .load(contacts.image)
                .error(R.drawable.ic_round_account_circle)
                .into(picture, object : Callback {
                    override fun onSuccess() {
                        progressBar.setVisible(false)
                    }

                    override fun onError(e: Exception?) {
                        progressBar.setVisible(false)
                    }
                })
        }
    }
}