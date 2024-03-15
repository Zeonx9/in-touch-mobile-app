package com.example.intouchmobileapp.domain.model

import com.example.intouchmobileapp.common.Constants.SERVER_URL
import java.time.LocalDate

data class User(
    val dateOfBirth: LocalDate,
    val id: Int,
    val isOnline: Boolean?,
    val patronymic: String?,
    val phoneNumber: String,
    val realName: String,
    val surname: String,
    val username: String,
    val profilePhotoId: String?,
    val thumbnailPhotoId: String?
) {
    val fullName: String
        get() = "$surname $realName ${patronymic ?: ""}"

    val abbreviation: String
        get() = "${surname[0]}${realName[0]}".uppercase()

    val photoUrl: String?
        get() = profilePhotoId?.let { id -> constructPictureUrl(id) }

    val thumbnailUrl: String?
        get() = thumbnailPhotoId?.let { id -> constructPictureUrl(id) }

    private fun constructPictureUrl(id: String): String = "${SERVER_URL}download/$id"
}