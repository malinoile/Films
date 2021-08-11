package ru.malinoil.films.model.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import ru.malinoil.films.model.TitleType
import java.util.*

data class FilmEntity(
    @SerializedName("title")
    val name: String,
    @SerializedName("original_title")
    val original: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    var rate: Float?,
    @SerializedName("overview")
    var description: String?
) : Parcelable {
    val id: String = UUID.randomUUID().toString()
    var imageSrc: String? = null
    var type: TitleType? = null
    var isFavorite: Boolean = false
    var budget: Int? = null
    var fees: Int? = null
    var genres: String? = null

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readString()
    ) {
        imageSrc = parcel.readString()
        isFavorite = parcel.readByte() != 0.toByte()
        budget = parcel.readValue(Int::class.java.classLoader) as? Int
        fees = parcel.readValue(Int::class.java.classLoader) as? Int
        genres = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(original)
        parcel.writeString(releaseDate)
        parcel.writeValue(rate)
        parcel.writeString(description)
        parcel.writeString(imageSrc)
        parcel.writeByte(if (isFavorite) 1 else 0)
        parcel.writeValue(budget)
        parcel.writeValue(fees)
        parcel.writeString(genres)
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