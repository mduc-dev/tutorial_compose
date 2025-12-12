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
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.UUID

object BUILDCONFIG {
    const val BASE_URL = "https://api.tap.io"

    private const val SEARCH_UID = "aee8172a-80ed-4b17-a9cb-68f3ad03d57e"
    private const val SEARCH_VID = "364066371"

    const val ACTION_ITEM_URL =
        "/i/app/v1/action-item?X-UA=V%3D1%26PN%3DTapLite%26VN_CODE%3D356061000%26VN%3D3.56.7-lite.100000%26LOC%3DVN%26LANG%3Den_US%26CH%3Ddefault%26UID%3Daee8172a-80ed-4b17-a9cb-68f3ad03d57e%26VID%3D364066371%26NT%3D1%26SR%3D1080x2332%26DEB%3Drealme%26DEM%3DRMX1931%26OSV%3D10%26CURR%3DVN"

    private fun buildXUA(
        locale: Locale = Locale.getDefault(),
        uidOverride: String? = null,
        vidOverride: String? = null
    ): String {
        val deviceBrand = Build.BRAND?.takeIf { it.isNotBlank() } ?: "unknown"
        val deviceModel = Build.MODEL?.takeIf { it.isNotBlank() } ?: "unknown"
        val osVersion = Build.VERSION.RELEASE?.takeIf { it.isNotBlank() } ?: "0"
        val country = locale.country.ifEmpty { "VN" }
        val lang = locale.toLanguageTag().replace('-', '_') // ex: en_US

        val params = mapOf(
            "V" to "1",
            "PN" to "TapIntl2",
            "VN_CODE" to "356061000",
            "VN" to "3.56.7-lite.100000",
            "LOC" to country,
            "LANG" to lang,
            "CH" to "default",
            "UID" to (uidOverride?.takeIf { it.isNotBlank() } ?: UUID.randomUUID().toString()),
            "VID" to (vidOverride?.takeIf { it.isNotBlank() } ?: (100000000..999999999).random()
                .toString()),
            "NT" to "1",
            "SR" to "1080x2332",
            "DEB" to deviceBrand,
            "DEM" to deviceModel,
            "OSV" to osVersion,
            "CURR" to country)

        val rawXua = params.entries.joinToString("&") { "${it.key}=${it.value}" }
        return URLEncoder.encode(rawXua, StandardCharsets.UTF_8.name())
    }

    fun newXUA(
        locale: Locale = Locale.getDefault(),
        uidOverride: String? = null,
        vidOverride: String? = null
    ): String = buildXUA(locale, uidOverride, vidOverride)

    fun ensureXua(url: String, xua: String): String {
        if (url.contains("X-UA=", ignoreCase = true)) {
            return url
        }
        val separator = if (url.contains("?")) "&" else "?"
        return "$url${separator}X-UA=$xua"
    }

    fun countMetricAccount(xua: String = newXUA()): String {
        return "$BASE_URL/account/v1/counts?X-UA=$xua)"
    }

    fun newErnBadge(xua: String = newXUA()): String {
        return "$BASE_URL/i/badge/v1/new-earned?X-UA=$xua"
    }

    fun miniMultiGet(xua: String = newXUA()): String {
        return "$BASE_URL/app/v1/mini-multi-get?X-UA=$xua"
    }

    fun userAppByIdentifiers(xua: String = newXUA()): String {
        return "$BASE_URL/user-app/v2/by-identifiers?X-UA=$xua"
    }

    fun userAppStatus(xua: String = newXUA()): String {
        return "$BASE_URL/i/user-app-status/v1/by-me?X-UA=$xua&source=1&status=0"
    }

    fun badgeWearInfoByMe(xua: String = newXUA()): String {
        return "$BASE_URL/i/badge/v1/wear-info-by-me?X-UA=$xua"
    }

    fun feedByMe(xua: String = newXUA()): String {
        return "$BASE_URL/feeds/v2/by-me?limit=10&X-UA=$xua&from=0&type=0"
    }

    fun creationFavorite(xua: String = newXUA()): String {
        return "$BASE_URL/creation/favorite/v1/multi-get?type=post&ids=7456704&X-UA=$xua"
    }

    fun creationVote(xua: String = newXUA()): String {
        return "$BASE_URL/creation/vote/v1/multi-get?type=post&ids=7456704&X-UA=$xua"
    }

    fun badgeListByMe(xua: String = newXUA()): String {
        return "$BASE_URL/i/badge/v1/list-by-me?limit=50&X-UA=$xua&from=0"
    }

    //TODO: ở đây cần header Authorization: MAC, XDT, S-SMFP, X-UT
    //| Header                 | Dùng để làm gì                                         | Ai tạo                  |
    //| ---------------------- | ------------------------------------------------------ | ----------------------- |
    //| **Authorization: MAC** | Xác thực request, chống giả mạo                        | App + secret key        |
    //| **X-DT**               | Device Trust Token: fingerprint thiết bị + anti-tamper | App (native layer)      |
    //| **X-SMFP**             | Fingerprint phiên làm việc                             | App                     |
    //| **X-UT**               | User token (login)                                     | Server trả về sau login |
    //| **User-Agent**         | Thư viện OkHttp                                        | App                     |

    fun userProfileUrl(xua: String = newXUA()): String {
        return "$BASE_URL/user-profile/v1/me?X-UA=$xua"
    }

    fun termBrand(xua: String = newXUA()): String {
        return "$BASE_URL/config/v1/term?brand=?realme&X-UA=$xua"
    }

    fun gameUrl(
        date: Date = Date(),
        xua: String = newXUA(),
    ): String {
        val formatter = SimpleDateFormat("yyyyMMdd", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val formattedDate = formatter.format(date)

        return "$BASE_URL/i/discover/v4/home?X-UA=$xua&date_key=$formattedDate"
    }

    fun instantPlay(xua: String = newXUA()): String {
        return "$BASE_URL/i/instant-game/v2/rec?X-UA=$xua"
    }

    fun randomInstantPlayGame(xua: String = newXUA()): String {
        return "$BASE_URL/i/instant-game/v1/random?X-UA=$xua"
    }

    fun searchPlaceholder(locale: Locale = Locale.getDefault()): String {
        val xua = newXUA(
            locale = locale, uidOverride = SEARCH_UID, vidOverride = SEARCH_VID
        )
        return "$BASE_URL/i/search/v2/placeholder?X-UA=$xua"
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
