package com.completetaskapp.pksq.data.models

import com.google.gson.annotations.SerializedName
import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
data class UserDeeplinks(
    @SerializedName("af_message")
    val af_message: String,
    @SerializedName("af_status")
    val af_status: String,
    @SerializedName("is_first_launch")
    val is_first_launch: Boolean,
    @SerializedName("install_time")
    val install_time: String,
    @SerializedName("campaign")
    val campaign: String?,
    @SerializedName("media_source")
    val media_source: String?,
    @SerializedName("agency")
    val agency: String?,
    @SerializedName("ad_id")
    val ad_id: String?,
    @SerializedName("adset_id")
    val adset_id: String?,
    @SerializedName("adgroup")
    val adgroup: String?,
    @SerializedName("adset")
    val adset: String?,
    @SerializedName("campaign_id")
    val campaign_id: String?
)