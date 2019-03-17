package com.giuseppebuzzanca.beerbox.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Beer(
    @PrimaryKey
    val id: Int,
    val name: String,
    @ColumnInfo(name = "tagline")
    @SerializedName("tagline")
    val tagLine: String?,
    val description: String?,
    @ColumnInfo(name = "image_url")
    @SerializedName("image_url")
    val imageUrl: String?,
    val ebc: Float
)