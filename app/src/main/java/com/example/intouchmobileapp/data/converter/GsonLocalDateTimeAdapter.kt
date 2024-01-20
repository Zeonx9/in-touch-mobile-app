package com.example.intouchmobileapp.data.converter

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.time.LocalDateTime
import java.lang.reflect.Type
import java.time.format.DateTimeFormatter

class GsonLocalDateTimeAdapter : JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    @RequiresApi(Build.VERSION_CODES.O)
    @Synchronized
    override fun serialize(
        date: LocalDateTime,
        type: Type,
        jsonSerializationContext: JsonSerializationContext
    ): JsonElement {
        return JsonPrimitive(date.format(DateTimeFormatter.ISO_DATE_TIME))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Synchronized
    override fun deserialize(
        jsonElement: JsonElement,
        type: Type,
        jsonDeserializationContext: JsonDeserializationContext
    ): LocalDateTime {
        return LocalDateTime.parse(jsonElement.asString)
    }
}
