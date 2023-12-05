package com.example.eva3firebase.genresCrud

import android.os.Parcel
import android.os.Parcelable

data class Genres(
    val id: String? = null,
    val genre: String? = null, ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
    ){

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(genre)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Genres> {
        override fun createFromParcel(parcel: Parcel?): Genres? {
            return parcel?.let { Genres(it) }
        }

        override fun newArray(size: Int): Array<Genres?> {
            return arrayOfNulls(size)
        }

    }

}