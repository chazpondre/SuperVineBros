package ca.thenightcrew.supervinebros.fragments

import ca.thenightcrew.supervinebros.game_engine.gameSave


class GameFragment : TouchFragment(){
    override fun onSaveGame(): gameSave {
        TODO("Not yet implemented")
    }

    override fun onTouchOrDragLeft() = slideViewModel.moveSprite("left", animationEngine);

    override fun onTouchOrDragRight() = slideViewModel.moveSprite("right", animationEngine);

    override fun onTouchOrDragTop() {
    }

    override fun onTouchOrDragBottom() {

    }
}


