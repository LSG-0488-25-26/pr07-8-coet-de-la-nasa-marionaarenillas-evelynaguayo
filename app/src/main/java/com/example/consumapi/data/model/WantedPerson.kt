package com.example.consumapi.data.model

import com.google.gson.annotations.SerializedName

// Respuesta principal de la API
data class WantedResponse(
    @SerializedName("total") val total: Int,
    @SerializedName("items") val items: List<WantedPerson>
)

// Cada persona buscada
data class WantedPerson(
    @SerializedName("uid") val uid: String,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("images") val images: List<WantedImage>?,
    @SerializedName("aliases") val aliases: List<String>?,
    @SerializedName("age_range") val ageRange: String?,
    @SerializedName("age_min") val ageMin: Int?,
    @SerializedName("age_max") val ageMax: Int?,
    @SerializedName("weight_min") val weightMin: String?,
    @SerializedName("weight_max") val weightMax: String?,
    @SerializedName("height_min") val heightMin: Int?,
    @SerializedName("height_max") val heightMax: Int?,
    @SerializedName("eyes") val eyes: String?,
    @SerializedName("hair") val hair: String?,
    @SerializedName("sex") val sex: String?,
    @SerializedName("race") val race: String?,
    @SerializedName("nationality") val nationality: String?,
    @SerializedName("place_of_birth") val placeOfBirth: String?,
    @SerializedName("dates_of_birth_used") val datesOfBirthUsed: List<String>?,
    @SerializedName("warning_message") val warningMessage: String?,
    @SerializedName("remarks") val remarks: String?,
    @SerializedName("details") val details: String?,
    @SerializedName("caution") val caution: String?,
    @SerializedName("reward_text") val rewardText: String?,
    @SerializedName("reward_min") val rewardMin: Int?,
    @SerializedName("reward_max") val rewardMax: Int?,
    @SerializedName("subjects") val subjects: List<String>?,
    @SerializedName("field_offices") val fieldOffices: List<String>?,
    @SerializedName("status") val status: String?,
    @SerializedName("url") val url: String?
)

// Imágenes del buscado
data class WantedImage(
    @SerializedName("original") val original: String?,
    @SerializedName("large") val large: String?,
    @SerializedName("thumb") val thumb: String?,
    @SerializedName("caption") val caption: String?
)