package ca.thenightcrew.supervinebros.levels

import android.content.res.Resources
import ca.thenightcrew.supervinebros.game_engine.level.components.SectionBuilder

// Recycler View
internal class Level(
    val levelIndex: Int,
    val title: String,
    val resourceId: Int,
    val buildLevel: (Resources) -> SectionBuilder
)