package ru.malinoil.films.model.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class GenreEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    var name: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GenreEntity> {
        override fun createFromParcel(parcel: Parcel): GenreEntity {
            return GenreEntity(parcel)
        }

        override fun newArray(size: Int): Array<GenreEntity?> {
            return arrayOfNulls(size)
        }
    }

}
