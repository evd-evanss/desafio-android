package com.picpay.desafio.android.domain.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContactsResponseFake(
    @SerializedName("img") val image: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("username") val username: String?
) : Parcelable