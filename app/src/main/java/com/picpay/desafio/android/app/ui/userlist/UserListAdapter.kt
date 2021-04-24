package com.picpay.desafio.android.app.ui.userlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.domain.model.local.User
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.android.synthetic.main.list_item_user.view.*
import javax.inject.Inject

@ActivityScoped
class UserListAdapter @Inject constructor():
    RecyclerView.Adapter<UserListAdapter.UserListItemViewHolder>() {

    var users: List<User> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_user, parent, false)

        return UserListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    class UserListItemViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User) {
            itemView.name.text = user.name
            itemView.username.text = user.username
            itemView.progressBar.visibility = View.VISIBLE
            Picasso.get()
                .load(user.image)
                .error(R.drawable.ic_round_account_circle)
                .into(itemView.picture, object : Callback {
                    override fun onSuccess() {
                        itemView.progressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        itemView.progressBar.visibility = View.GONE
                    }
                })
        }
    }
}