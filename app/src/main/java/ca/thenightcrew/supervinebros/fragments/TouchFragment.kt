package ca.thenightcrew.supervinebros.fragments

import android.graphics.Point
import android.view.Display
import android.view.MotionEvent
import android.view.View

abstract class TouchFragment : LevelBinderFragment() {
    private var controlsAreDisabled = false
    private var halfWidth = 0
    private var halfHeight = 0

    override fun setUpView(view: View) {
        super.setUpView(view)
        if (!setUpDisplayDimensions()) throw Error("Dimensions are not set up");
        view.setOnTouchListener { _, event ->
            handleTouchEvents(event)
            when (event.action) {
                MotionEvent.ACTION_UP -> view.performClick()
                else -> {}
            }
            true
        }
    }

    private fun setUpDisplayDimensions(): Boolean {
        if (activity != null) {
            val display: Display = requireActivity().windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            halfWidth = size.x / 2
            halfHeight = size.y / 2
        }
        return halfWidth > 0 && halfHeight > 0
    }

    private fun handleTouchEvents(motionEvent: MotionEvent): Boolean {
        if (controlsAreDisabled) return false
        val x = motionEvent.getAxisValue(0)
        val y = motionEvent.getAxisValue(1)
        if (x < halfWidth) onTouchOrDragLeft() else onTouchOrDragRight()
        if (y < halfHeight) onTouchOrDragTop() else onTouchOrDragBottom()
        return true
    }

    fun disableController(controlsAreDisabled: Boolean) {
        this.controlsAreDisabled = controlsAreDisabled
    }

    abstract fun onTouchOrDragLeft()
    abstract fun onTouchOrDragRight()
    abstract fun onTouchOrDragTop()
    abstract fun onTouchOrDragBottom()

    val screenWidth: Int
        get() = halfWidth * 2
    val screenHeight: Int
        get() = halfHeight * 2
}