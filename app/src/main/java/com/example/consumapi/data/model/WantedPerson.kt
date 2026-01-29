package com.example.consumapi.data.model

import com.google.gson.annotations.SerializedName

data class WantedResponse(

    @SerializedName("total")
    val total: Int,

    @SerializedName("page")
    val page: Int?,

    @SerializedName("items")
    val items: List<WantedPerson>
)

// Cada persona buscada
data class WantedPerson(

    @SerializedName("uid")
    val uid: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("remarks")
    val remarks: String?,

    @SerializedName("publication")
    val publication: String?,

    @SerializedName("reward_text")
    val rewardText: String?,

    @SerializedName("caution")
    val caution: String?,

    @SerializedName("category")
    val category: String?,

    @SerializedName("nationality")
    val nationality: String?,

    @SerializedName("sex")
    val sex: String?,

    @SerializedName("race")
    val race: String?,

    @SerializedName("hair")
    val hair: String?,

    @SerializedName("eyes")
    val eyes: String?,

    @SerializedName("height")
    val height: String?,

    @SerializedName("weight")
    val weight: String?,

    @SerializedName("place_of_birth")
    val placeOfBirth: String?,

    @SerializedName("dates_of_birth_used")
    val datesOfBirthUsed: List<String>?,

    @SerializedName("images")
    val images: List<WantedImage>?
)

// Imágenes del buscado
data class WantedImage(

    @SerializedName("thumb")
    val thumb: String?,

    @SerializedName("original")
    val original: String?
)