package io.castled.inappNotifications.database

import androidx.lifecycle.LiveData
import io.castled.inappNotifications.models.NotificationModel

interface NotificationDatabaseHelper {

    suspend fun getNotificationsFromDb(): List<NotificationModel>

    suspend fun getLiveDataNotificationsFromDb(): LiveData<List<NotificationModel>>

    suspend fun insertNotificationsIntoDb(notifications: List<NotificationModel>): LongArray

    suspend fun deleteDbNotifications(): Int

}