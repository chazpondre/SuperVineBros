package ca.thenightcrew.supervinebros.fragments

import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import ca.thenightcrew.supervinebros.R
import ca.thenightcrew.supervinebros.levels.MenuAdapter
import ca.thenightcrew.supervinebros.levels.appLevels


private val tintList = intArrayOf(
    Color.argb(0, 0, 0, 0),
    Color.argb(60, 50, 0, 55),
    Color.argb(60, 50, 85, 65),
    Color.argb(60, 200, 20, 55)
)

class LevelSelectorFragment : Fragment() {
    private val menuBg: View by lazy { requireView().findViewById(R.id.menu_bg) }
    private val levelRecycler: RecyclerView by lazy { requireView().findViewById(R.id.level_recycler) }
    private val levelLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_menu, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        levelRecycler.configure()
    }

    private fun RecyclerView.configure() {
        layoutManager = levelLayoutManager
        MenuAdapter().also {
            adapter = it
            it.submitList(appLevels)
        }
        // TODO recyclerView.scrollToPosition(viewModel.getRecyclerPosition());
        val snapper = PagerSnapHelper().attachToRecyclerView(this)
        configureScrollListener()
    }

    private fun RecyclerView.configureScrollListener() =
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val position: Int = levelLayoutManager.findFirstVisibleItemPosition()
                    val lastColor = (menuBg.background as ColorDrawable).color

                    val animator = ObjectAnimator.ofArgb(menuBg, "backgroundColor", lastColor, tintList[position])
                    animator.duration = 500
                    animator.interpolator = AccelerateDecelerateInterpolator()
                    animator.start()

                    //Todo viewModel.setRecyclerPosition(position);
                }
            }
        })
}


