package com.example.eva3firebase.booksCrud

import android.os.Parcel
import android.os.Parcelable

data class Books(
    val id: String? = null,
    val title: String? = null,
    val author: String? = null,
    val publicationDate: String? = null,
    val binding: String? = null,
    val pages: Int? = null,
    val urlImage: String? = null): Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString()
        ){

        }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(author)
        parcel.writeString(publicationDate)
        parcel.writeString(binding)
        parcel.writeValue(pages)
        parcel.writeString(urlImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Books> {
        override fun createFromParcel(parcel: Parcel?): Books? {
            return parcel?.let { Books(it) }
        }

        override fun newArray(size: Int): Array<Books?> {
            return arrayOfNulls(size)
        }

    }

    }




