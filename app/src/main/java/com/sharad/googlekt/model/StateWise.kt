package com.sharad.googlekt.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StateWise(
    @Json(name = "statewise")
    val stateWiseDetails: List<Details>
) : Parcelable