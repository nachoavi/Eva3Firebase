package com.example.eva3firebase.registerUser

import android.os.Parcel
import android.os.Parcelable

data class Users(
    val id: String? = null,
    val userType: Int? = 0,
    val username: String? = null,
    val password: String? = null,
    val email: String? = null,
   ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
    ){

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeValue(userType)
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeString(email)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Users> {
        override fun createFromParcel(parcel: Parcel?): Users? {
            return parcel?.let { Users(it) }
        }

        override fun newArray(size: Int): Array<Users?> {
            return arrayOfNulls(size)
        }

    }

}