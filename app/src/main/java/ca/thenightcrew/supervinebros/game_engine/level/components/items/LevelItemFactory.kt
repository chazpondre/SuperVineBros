package ca.thenightcrew.supervinebros.game_engine.level.components.items

import ca.thenightcrew.supervinebros.game_engine.level.components.items.types.*


/** A factory class that helps with the generation of Level items used in the RecyclerView  */
object LevelItemFactory {
    fun createLevelItem(levelItemType: LevelItemType): LevelItem? {
        if (levelItemType === LevelItemType.BigCoin) return BigCoinItem() else if (levelItemType === LevelItemType.Boo) return BooItem() else if (levelItemType === LevelItemType.Coins) return CoinItem() else if (levelItemType === LevelItemType.Goomba) return GoombaItem() else if (levelItemType === LevelItemType.KoopaTroopa) return KoopaTroopaItem()
        return null
    }
}