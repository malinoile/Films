package ru.malinoil.films.model.entities

import android.os.Parcel
import android.os.Parcelable
import ru.malinoil.films.model.TitleType
import java.util.*

data class FilmEntity(
    val name: String,
    val year: Int,
    var rate: Float?,
    var description: String?,
    var imageSrc: String?,
    var type: TitleType?
) : Parcelable {
    val id: String = UUID.randomUUID().toString()
    var isFavorite: Boolean = false
    var budget: Int? = null
    var fees: Int? = null
    var genres: String? = null
    var originalName: String? = null

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readString(),
        parcel.readString(),
        TODO("type")
    ) {
        isFavorite = parcel.readByte() != 0.toByte()
        budget = parcel.readValue(Int::class.java.classLoader) as? Int
        fees = parcel.readValue(Int::class.java.classLoader) as? Int
        genres = parcel.readString()
        originalName = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(year)
        parcel.writeValue(rate)
        parcel.writeString(description)
        parcel.writeString(imageSrc)
        parcel.writeByte(if (isFavorite) 1 else 0)
        parcel.writeValue(budget)
        parcel.writeValue(fees)
        parcel.writeString(genres)
        parcel.writeString(originalName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FilmEntity> {
        override fun createFromParcel(parcel: Parcel): FilmEntity {
            return FilmEntity(parcel)
        }

        override fun newArray(size: Int): Array<FilmEntity?> {
            return arrayOfNulls(size)
        }
    }


}