package ru.malinoil.films.model.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class FilmEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val name: String,
    @SerializedName("adult")
    val isAdult: Boolean,
    @SerializedName("original_title")
    val original: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    var rate: Float?,
    @SerializedName("overview")
    var description: String?,
    @SerializedName("poster_path")
    val poster: String?,
    @SerializedName("genres")
    var genres: List<GenreEntity>? = null,
    @SerializedName("budget")
    var budget: String? = null,
    @SerializedName("revenue")
    var fees: String? = null

) : Parcelable {
    var type: TitleType? = null
    var isFavorite: Boolean = false

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(GenreEntity),
        parcel.readString(),
        parcel.readString()
    ) {
        isFavorite = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeByte(if (isAdult) 1 else 0)
        parcel.writeString(original)
        parcel.writeString(releaseDate)
        parcel.writeValue(rate)
        parcel.writeString(description)
        parcel.writeString(poster)
        parcel.writeTypedList(genres)
        parcel.writeString(budget)
        parcel.writeString(fees)
        parcel.writeByte(if (isFavorite) 1 else 0)
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

    fun getGenresString(): String {
        val stringBuilder = StringBuilder()
        genres?.forEach {
            stringBuilder.append(", ").append(it.name)
        }
        return stringBuilder.toString()
    }
}