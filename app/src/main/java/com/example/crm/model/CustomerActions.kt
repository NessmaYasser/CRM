package com.example.crm.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CustomerActions(
    val ActionType : String? = null,
    val ActionDetails : String? = null
): Parcelable
