package ca.thenightcrew.supervinebros.fragments

import android.util.Log
import ca.thenightcrew.supervinebros.database.Coins
import ca.thenightcrew.supervinebros.db
import ca.thenightcrew.supervinebros.game_engine.AppInfo
import ca.thenightcrew.supervinebros.game_engine.Utils
import kotlinx.coroutines.runBlocking


class GameFragment : TouchFragment() {

    override fun onTouchOrDragLeft() = slideViewModel.moveSprite("left", animationEngine);

    override fun onTouchOrDragRight() = slideViewModel.moveSprite("right", animationEngine);

    override fun onTouchOrDragTop() {
    }

    override fun onTouchOrDragBottom() {

    }
}


