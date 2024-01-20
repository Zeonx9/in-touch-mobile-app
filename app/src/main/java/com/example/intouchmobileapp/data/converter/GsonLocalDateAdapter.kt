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

class GsonLocalDateAdapter : JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    @Synchronized
    override fun serialize(
        date: LocalDate,
        type: Type,
        jsonSerializationContext: JsonSerializationContext
    ): JsonElement {
        return JsonPrimitive(date.format(DateTimeFormatter.ISO_DATE))
    }

    @Synchronized
    override fun deserialize(
        jsonElement: JsonElement,
        type: Type,
        jsonDeserializationContext: JsonDeserializationContext
    ): LocalDate {
        return LocalDate.parse(jsonElement.asString)
    }
}