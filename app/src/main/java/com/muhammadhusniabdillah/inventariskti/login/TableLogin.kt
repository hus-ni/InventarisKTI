package com.muhammadhusniabdillah.inventariskti.login

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class TableLogin(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    var username: String,
    var password: String,
    var email: String?
) : Parcelable
