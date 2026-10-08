package com.amir.askari.saet.shared.data.remote

import kotlinx.serialization.json.Json

val ProductJson: Json = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
}
