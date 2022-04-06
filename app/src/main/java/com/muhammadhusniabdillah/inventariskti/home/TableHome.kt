package com.muhammadhusniabdillah.inventariskti.home

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class TableHome(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    var barcode: String,
    var spec: String,
    var location: String,
    var pic: String,
    var condition: String
) : Parcelable
