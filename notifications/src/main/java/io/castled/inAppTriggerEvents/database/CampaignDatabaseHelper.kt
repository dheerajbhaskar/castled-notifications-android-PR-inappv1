package io.castled.inAppTriggerEvents.database

import androidx.lifecycle.LiveData
import io.castled.inAppTriggerEvents.models.CampaignModel

internal interface CampaignDatabaseHelper {

    suspend fun getCampaignsFromDb(): List<CampaignModel>

    suspend fun getLiveDataCampaignsFromDb(): LiveData<List<CampaignModel>>

    suspend fun insertCampaignsIntoDb(campaigns: List<CampaignModel>): LongArray

    suspend fun deleteDbCampaigns(): Int

    suspend fun updateDbCampaignLastDisplayed(campaignModel: CampaignModel)

    suspend fun updateDbCampaignLastDisplayed(timeDisplayed: Long, lastDisplayedTime: Long, id: Int, notificationId: Int): Int

}