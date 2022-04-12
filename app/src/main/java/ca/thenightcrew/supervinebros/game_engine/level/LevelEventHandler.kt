package ca.thenightcrew.supervinebros.game_engine.level

import android.util.Log
import android.view.View
import ca.thenightcrew.supervinebros.animation.AnimationEngine

/**
 * A LevelEventHandler observers events from a Level Event observer
 */
interface LevelEventHandler {
    val slideViewModel: SlideViewModel
    val animationEngine: AnimationEngine
    val backButton: View
    val welcomeScreen: View

    /** The event called when a level is completed */
    fun onLevelComplete(){
        Log.d("Level Debug", "Level Complete")
        slideViewModel.handleLevelComplete(animationEngine)
    }

    /** The event called when another level need to be loaded  */
    fun onNextLevel(){
        backButton.callOnClick()
    }

    /** The event called when the game has no more levels */
    fun onGameComplete(){
        Log.d("Level Debug", "Complete")
    }

    /** The event called when a Level Object has been interacted with */
    fun onInteraction(interactionEvent: InteractionEvent){
        Log.d("Level Debug", "Complete")
        slideViewModel.handleInteraction(interactionEvent, animationEngine)
    }

    /** The event called when a level is Loaded */
    fun onLoadLevel(){
        Log.d("Level Debug", "Complete")
        slideViewModel.startLevel(0, animationEngine);
        welcomeScreen.visibility = View.GONE;
    }
}
