package com.example.intouchmobileapp.data.converter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GsonWSLocalDateAdapter : JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    @Synchronized
    override fun serialize(
        date: LocalDate,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        return JsonPrimitive(date.format(DateTimeFormatter.ISO_DATE))
    }

    @Synchronized
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): LocalDate {
        val arr = json.asJsonArray.map { it.asInt }
        return LocalDate.of(arr[0], arr[1], arr[2])
    }
}
