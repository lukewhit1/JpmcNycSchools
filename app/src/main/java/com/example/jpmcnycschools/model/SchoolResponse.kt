package com.example.jpmcnycschools.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SchoolResponse(
    val dbn: String,
    val school_name: String,
    val phone_number: String,
    val school_email: String,
    val website: String,
    val primary_address_line_1: String,
    val overview_paragraph: String
): Parcelable

data class SatResponse(
    val dbn: String,
    @SerializedName("sat_critical_reading_avg_score")
    val readingAvg: String,
    @SerializedName("sat_math_avg_score")
    val mathAvg: String,
    @SerializedName("sat_writing_avg_score")
    val writingAvg: String
)
