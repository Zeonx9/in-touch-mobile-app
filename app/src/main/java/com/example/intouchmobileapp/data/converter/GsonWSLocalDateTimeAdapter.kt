package com.example.intouchmobileapp.data.converter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GsonWSLocalDateTimeAdapter : JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    @Synchronized
    override fun serialize(
        date: LocalDateTime,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        return JsonPrimitive(date.format(DateTimeFormatter.ISO_DATE_TIME))
    }

    @Synchronized
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext?
    ): LocalDateTime {
        val arr = json.asJsonArray.map { it.asInt }
        return LocalDateTime.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6])
    }

}