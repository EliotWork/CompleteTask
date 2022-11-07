package com.completetaskapp.pksq.data.models

import com.google.gson.annotations.SerializedName
import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
data class ConfigSettings(
    @SerializedName("approve")
    val probe: Boolean? = null,
    @SerializedName("path")
    val path: String? = null
)