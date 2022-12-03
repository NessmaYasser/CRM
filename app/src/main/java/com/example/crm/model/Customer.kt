package com.example.crm.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Customer")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    val Id: Int = 0,
    val Name: String,
    val Actions: List<CustomerActions>?
) : Parcelable

