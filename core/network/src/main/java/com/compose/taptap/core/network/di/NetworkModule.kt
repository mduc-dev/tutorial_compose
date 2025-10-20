package com.compose.taptap.core.network.di

import android.os.Build
import android.util.Log
import com.compose.taptap.core.network.service.TapTapClient
import com.compose.taptap.core.network.service.TapTapService
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.addDefaultResponseValidation
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.UUID

object BUILDCONFIG {
    const val BASE_URL = "https://api.tap.io"

    //Game
    private const val GAME_URL_TEMPLATE = "/i/discover/v4/home?X-UA=%XUA%&date_key=%DATE_KEY%"

    //PLay
    private const val INSTANT_GAME_TEMPLATE = "/i/instant-game/v2/rec?X-UA=%XUA%"

    private const val XUA_PLACEHOLDER = "%XUA%"
    private const val DATE_KEY_PLACEHOLDER = "%DATE_KEY%"

    private fun buildXUA(locale: Locale = Locale.getDefault()): String {
        val deviceBrand = Build.BRAND ?: "unknown"
        val deviceModel = Build.MODEL ?: "unknown"
        val osVersion = Build.VERSION.RELEASE ?: "0"
        val country = locale.country.ifEmpty { "VN" }
        val lang = locale.toLanguageTag().replace('-', '_') // ex: en_US

        val params = mapOf(
            "V" to "1",
            "PN" to "TapLite",
            "VN_CODE" to "356061000",
            "VN" to "3.56.6-lite.100000",
            "LOC" to country,
            "LANG" to lang,
            "CH" to "default",
            "UID" to UUID.randomUUID().toString(),
            "VID" to (100000000..999999999).random().toString(),
            "NT" to "1",
            "SR" to "1080x2332",
            "DEB" to deviceBrand,
            "DEM" to deviceModel,
            "OSV" to osVersion,
            "CURR" to country
        )

        // Encode để an toàn trong query
        return params.entries.joinToString("%26") { "${it.key}%3D${it.value}" }
    }

    fun gameUrl(
        date: Date = Date()
    ): String {
        val formatter = SimpleDateFormat("yyyyMMdd", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val formattedDate = formatter.format(date)
        val xua = buildXUA()

        return BASE_URL + GAME_URL_TEMPLATE.replace(XUA_PLACEHOLDER, xua)
            .replace(DATE_KEY_PLACEHOLDER, formattedDate)
    }

    fun instantPlay(): String {
        val xua = buildXUA()

        return BASE_URL + INSTANT_GAME_TEMPLATE.replace(XUA_PLACEHOLDER, xua)
    }

}

fun networkModule() = module {
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }
    /**
     * Creates a http client for Ktor that is provided to the
     * API client via constructor injection
     */
    single {
        val json = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        }

        HttpClient(Android) {
            expectSuccess = true
            addDefaultResponseValidation()

            install(ContentNegotiation) {
                json(json)
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("Http Client", message)
                    }
                }
                level = LogLevel.ALL
            }
            install(DefaultRequest) {
                url(BUILDCONFIG.BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }

    single<TapTapService> { TapTapClient(get()) }
}