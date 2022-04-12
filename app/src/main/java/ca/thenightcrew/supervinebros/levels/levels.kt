package ca.thenightcrew.supervinebros.levels

import android.content.res.Resources
import ca.thenightcrew.supervinebros.R
import ca.thenightcrew.supervinebros.game_engine.level.components.SectionBuilder
import ca.thenightcrew.supervinebros.game_engine.level.components.items.types.LevelItemType


internal val appLevels = mutableListOf(
    Level(0,"Level 1", R.drawable.menu_vine_bros) { resources: Resources -> LevelOne(resources) },
    Level(1, "Level 2", R.drawable.menu_vine_bros) { resources: Resources -> LevelTwo(resources) },
    Level(2, "Level 3", R.drawable.menu_vine_bros) { resources: Resources -> LevelTwo(resources) },
    Level(3, "Level 4", R.drawable.menu_vine_bros) { resources: Resources -> LevelTwo(resources) }
)


class LevelOne(resources: Resources) : SectionBuilder(resources) {
    init {
        levelWorstTime = 100
        addVine(R.drawable.slide_repeated_vines).repeatBackground(2).addToLevel()
        addVine(R.drawable.slide_repeated_vines)
            .addLevelItem(LevelItemType.Coins, 1)
            .addBackground(R.drawable.slide_background_black_500)
            .repeatBackground(4)
            .addToLevel()
        addVine(R.drawable.slide_repeated_vines).repeatBackground(2).addToLevel()
        addVine(R.drawable.slide_repeated_vines)
            .addBackground(R.drawable.slide_background_cacti_500)
            .addLevelItem(LevelItemType.Coins, 1)
            .addLevelItem(LevelItemType.KoopaTroopa, 5)
            .repeatBackground(2)
            .addToLevel()
        addVine(R.drawable.slide_repeated_vines)
            .repeatBackground(2)
            .addLevelItem(LevelItemType.KoopaTroopa, 5)
            .addToLevel()
        addVine(R.drawable.slide_repeated_vines)
            .addBackground(R.drawable.slide_background_purple_500)
            .repeatBackground(0)
            .addToLevel()
        addVine(R.drawable.slide_repeated_vines)
            .addLevelItem(LevelItemType.Goomba, 1)
            .addBackground(R.drawable.slide_background_wave_purple_500)
            .repeatBackground(0)
            .addToLevel()
        addVine(R.drawable.slide_repeated_vines).repeatBackground(2).addToLevel()
    }
}

class LevelTwo(resources: Resources) : SectionBuilder(resources) {
    init {
        levelWorstTime = 100
        addVine(R.drawable.slide_repeated_vines).repeatBackground(2).addToLevel()
        addVine(R.drawable.slide_repeated_vines)
            .addLevelItem(LevelItemType.Goomba, 1)
            .addBackground(R.drawable.slide_background_weird_eyes)
            .repeatBackground(3)
            .addToLevel()
        addVine(R.drawable.slide_repeated_vines).repeatBackground(2)
            .addLevelItem(LevelItemType.Coins, 1)
            .addToLevel()
        addVine(R.drawable.slide_repeated_vines)
            .addBackground(R.drawable.slide_background_purple_500)
            .addLevelItem(LevelItemType.Goomba, 1)
            .repeatBackground(4)
            .addToLevel()
        addVine(R.drawable.slide_repeated_vines)
            .addBackground(R.drawable.slide_background_cacti_500)
            .addLevelItem(LevelItemType.Coins, 1)
            .addLevelItem(LevelItemType.KoopaTroopa, 4)
            .repeatBackground(3)
            .addToLevel()
        addVine(R.drawable.slide_repeated_vines)
            .addLevelItem(LevelItemType.Goomba, 1)
            .addBackground(R.drawable.slide_background_catci_pot_500)
            .repeatBackground(0)
            .addToLevel()
        addVine(R.drawable.slide_repeated_vines).repeatBackground(2).addToLevel()
    }
}

