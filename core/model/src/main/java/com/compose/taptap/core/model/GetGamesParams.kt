package com.compose.taptap.core.model

/**
 * Parameters for getting games list with filtering and sorting options.
 */
data class GetGamesParams(
    val category: String? = null,
    val sortBy: GameSortType = GameSortType.POPULAR,
    val filterType: GameFilterType = GameFilterType.ALL
)

/**
 * Sorting options for games list.
 */
enum class GameSortType {
    POPULAR,    // Sort by popularity/downloads
    NEWEST,     // Sort by release date
    RATING      // Sort by user rating
}

/**
 * Filter options for games list.
 */
enum class GameFilterType {
    ALL,            // Show all games
    FREE,           // Free games only
    PAID,           // Paid games only
    EDITORS_CHOICE  // Editor's choice games
}
