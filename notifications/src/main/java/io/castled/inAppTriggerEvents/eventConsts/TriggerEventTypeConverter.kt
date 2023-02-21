package io.castled.inAppTriggerEvents.eventConsts

import androidx.room.TypeConverter
import com.google.gson.JsonObject
import com.google.gson.JsonParser

/**
 * This class will be used by ROOM to convert appropriate data type to save it into database.
 */
internal class TriggerEventTypeConverter {

    @TypeConverter
    fun fromJsonObject(source: JsonObject?): String {

        return source?.toString() ?: return ""
    }

    @TypeConverter
    fun toJsonObject(source: String): JsonObject {
        return JsonParser().parse(source).asJsonObject
    }
}