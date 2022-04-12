package ca.thenightcrew.supervinebros.game_engine.level.components

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView


/**
 * A customized recyclerView that ignore touch events
 */
class LevelRecyclerView : RecyclerView {
    // Standard Constructors
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    // Disable RecyclerView Touch Consumption
    override fun onTouchEvent(e: MotionEvent): Boolean {
        return false
    }
}