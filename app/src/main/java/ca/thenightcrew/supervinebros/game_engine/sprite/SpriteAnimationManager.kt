package ca.thenightcrew.supervinebros.game_engine.sprite

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import ca.thenightcrew.supervinebros.animation.*
import ca.thenightcrew.supervinebros.game_engine.level.LevelStatusEnum

/** The Sprite animation manager store all the animations that a sprite can show  */
class SpriteAnimationManager {
    private var isMoving = false

    /**
     * The animation the makes the sprite look like it is climbing the vine and also animates eye
     * blinking
     *
     * @param animationEngine The animation engine used in this game
     * @param legs The image view holding the legs of the sprite
     * @param hands The image view holding the hands of the sprite
     * @param blink The image view holding the blinked eye of the sprite
     */
    fun onVineAnimation(
        animationEngine: AnimationEngine,
        legs: ImageView,
        hands: ImageView,
        blink: ImageView
    ) {
        animationEngine.addKeyframe(
            "hands & legs",
            0.5f,
            object : KeyFrame(KeyTime(0f, 0.25f), false) {
                override fun onDefaultFrame(frameNumber: Int, totalFramesCalled: Int) {
                    legs.translationY = 0f
                    hands.translationY = 0f
                }

                override fun onKeyframe(frameNumber: Int, totalFramesCalled: Int) {
                    legs.translationY = -2f
                    hands.translationY = 5f
                }
            })

        val blinkKeyTimeList: MutableList<KeyTime> = ArrayList()
        blinkKeyTimeList.add(KeyTime(1.5f, 1.6f))
        blinkKeyTimeList.add(KeyTime(1.8f, 1.9f))
        animationEngine.addKeyframe(
            "blink", 2f,
            object : KeyFrame(blinkKeyTimeList, false) {
                override fun onDefaultFrame(frameNumber: Int, totalFramesCalled: Int) {
                    blink.visibility = View.INVISIBLE
                }

                override fun onKeyframe(frameNumber: Int, totalFramesCalled: Int) {
                    if (!isMoving) blink.visibility = View.VISIBLE
                }
            })
    }

    /**
     * Helps animate the sprite moving from side to side by show and hiding different elements
     *
     * @param animationEngine The animation engine used in this game
     * @param onVineSpriteComponents The components of the sprite on the vine in a list
     * @param middle The image view that holds the sprite that is in the middle of the vine
     */
    fun onMoveToSideAnimation(
        animationEngine: AnimationEngine,
        onVineSpriteComponents: List<ImageView>,
        middle: ImageView
    ) {
        animationEngine.addKeyframe(
            "move",
            0.14f,
            object : KeyFrame(KeyTime(0f, 0.09f), false, 0) {
                override fun onDefaultFrame(frameNumber: Int, totalFramesCalled: Int) {
                    for (component in onVineSpriteComponents) {
                        component.visibility = View.VISIBLE
                    }
                    middle.visibility = View.INVISIBLE
                    isMoving = false
                }

                override fun onKeyframe(frameNumber: Int, totalFramesCalled: Int) {
                    for (component in onVineSpriteComponents) {
                        component.visibility = View.INVISIBLE
                    }
                    middle.visibility = View.VISIBLE
                    isMoving = true
                }
            })
    }

    /**
     * Show the sprite dropping down from the vine then walking and finally showing a peace sign
     *
     * @param animationEngine The animation engine used in this game
     * @param onVineSpriteComponents The components of the sprite on the vine in a list
     * @param walkSpriteComponents The components of the sprite that animate walking
     * @param peaceSprite The image view that holds the peace sprite
     * @param middle The image view that holds the sprite that is in the middle of the vine
     * @param levelStatusMutableObservable An observable that lets the game fragment know that the
     * animation is over
     */
    fun endLevelAnimation(
        animationEngine: AnimationEngine,
        onVineSpriteComponents: List<ImageView>,
        walkSpriteComponents: List<ImageView>,
        peaceSprite: ImageView,
        middle: ImageView,
        levelStatusMutableObservable: MutableLiveData<LevelStatusEnum>
    ) {
        animationEngine.removeKeyframe("blink")
        animationEngine.removeKeyframe("move")
        animationEngine.removeKeyframe("hands & legs")
        middle.visibility = View.INVISIBLE
        for (onVineSpriteComponent in onVineSpriteComponents) {
            onVineSpriteComponent.visibility = View.VISIBLE
        }
        val fallenSpriteTranslationX: Float = onVineSpriteComponents[0].translationX
        for (walkComponent in walkSpriteComponents) {
            walkComponent.translationX = fallenSpriteTranslationX
        }
        val peaceSpriteYPosition: Float = peaceSprite.y
        animationEngine.addKeyframe(
            "sprite falling", 10f,
            object : SimpleKeyFrame(KeyTime(0f, 10f), true, 0) {
                var originalYPositions: ArrayList<Float> = ArrayList()
                override fun onKeyframe(frameNumber: Int, totalFramesCalled: Int) {
                    val animateFalling: Boolean =
                        onVineSpriteComponents[0].y + 40 <= peaceSpriteYPosition
                    if (animateFalling) for (component in onVineSpriteComponents) {
                        component.y = component.y + 40
                    } else {
                        for (component in onVineSpriteComponents) {
                            component.visibility = View.INVISIBLE
                        }
                        // Reset Sprite positions positions
                        for (i in 0 until onVineSpriteComponents.size) {
                            val onVineSpriteComponent: ImageView = onVineSpriteComponents[i]
                            onVineSpriteComponent.y = originalYPositions[i]
                        }
                        animationEngine.removeKeyframe("sprite falling")
                        walkAndPeaceAnimation(
                            animationEngine,
                            walkSpriteComponents,
                            peaceSprite,
                            levelStatusMutableObservable
                        )
                    }
                }

                init {
                    for (onVineSpriteComponent in onVineSpriteComponents) {
                        originalYPositions.add(onVineSpriteComponent.y)
                    }
                }
            })
    }

    /** The Sub sequence that shows the sprite walking  */
    private fun walkAndPeaceAnimation(
        animationEngine: AnimationEngine,
        walkComponents: List<ImageView>,
        peaceSprite: ImageView,
        levelStatusMutableObservable: MutableLiveData<LevelStatusEnum>
    ) {
        val animationLength = 8
        val frameRate = animationEngine.getFrameRate()
        val walkAnimationLengthInFrames = (animationLength - 1) * frameRate
        animationEngine.addKeyframe(
            "walk sequence", 10f,
            object : SimpleKeyFrame(KeyTime(0f, animationLength.toFloat()), true, 0) {
                override fun onKeyframe(frameNumber: Int, totalFramesCalled: Int) {
                    if (frameNumber < walkAnimationLengthInFrames) {
                        val visibleSpriteIndex = frameNumber % 60 / 20
                        for (i in 0 until walkComponents.size) {
                            val component: ImageView = walkComponents[i]
                            if (i == visibleSpriteIndex) component.visibility = View.VISIBLE else component.visibility =
                                View.INVISIBLE
                            if (component.translationX != 0f) {
                                val increment = if (component.translationX < 0) 1 else -1
                                val translationX: Float = increment + component.translationX
                                component.translationX = translationX
                            }
                        }
                    } else {
                        for (components in walkComponents) {
                            components.visibility = View.INVISIBLE
                        }
                        showPeaceForTwo(animationEngine, peaceSprite, levelStatusMutableObservable)
                    }
                }
            })
    }

    /** The Sub sequence that shows the sprite showing the peace sign  */
    private fun showPeaceForTwo(
        animationEngine: AnimationEngine,
        peaceSprite: View,
        levelStatusMutableObservable: MutableLiveData<LevelStatusEnum>
    ) {
        animationEngine.addKeyframe(
            "peace", 3f,
            object : ConstantKeyFrame() {
                var firstFrame = -1
                override fun onEachFrame(frameNumber: Int) {
                    if (firstFrame == -1) firstFrame = frameNumber
                    peaceSprite.visibility = View.VISIBLE
                    if (frameNumber - firstFrame > 30) {
                        animationEngine.removeKeyframe("peace")
                        peaceSprite.visibility = View.INVISIBLE
                        levelStatusMutableObservable.value = LevelStatusEnum.LEVEL_NEXT
                    }
                }
            })
    }

    /**
     * Shows the sprite temporarily getting hurt
     *
     * @param animationEngine The animation engine used in this game
     * @param onVineSpriteComponents The components of the sprite on the vine in a list
     * @param hurt The ImageView containing the hurt sprite
     * @param middle The ImageView containing the middle sprite
     */
    fun hurtAnimation(
        animationEngine: AnimationEngine,
        onVineSpriteComponents: List<ImageView>,
        hurt: ImageView,
        middle: ImageView
    ) {
        middle.alpha = 0f
        hurt.alpha = 1f
        for (component in onVineSpriteComponents) {
            component.alpha = 0f
        }
        animationEngine.addOnEachAnimationFrame(
            "sprite_hurt",
            object : ConstantKeyFrame() {
                var firstFrame = -1
                var isTransparent = false
                override fun onEachFrame(frameNumber: Int) {
                    if (firstFrame == -1) firstFrame = frameNumber
                    val currentFrame = frameNumber - firstFrame
                    if (currentFrame % 5 == 0) {
                        hurt.alpha = if (isTransparent) 1f else 0f
                        isTransparent = !isTransparent
                    }
                    if (currentFrame > 60) {
                        middle.alpha = 1f
                        for (component in onVineSpriteComponents) {
                            component.alpha = 1f
                        }
                        hurt.alpha = 0f
                        animationEngine.removeKeyframe("sprite_hurt")
                    }
                }
            })
    }
}