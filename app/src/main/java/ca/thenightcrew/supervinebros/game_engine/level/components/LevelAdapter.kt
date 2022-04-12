package ca.thenightcrew.supervinebros.game_engine.level.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ca.thenightcrew.supervinebros.R
import ca.thenightcrew.supervinebros.game_engine.SlideUtils.getLevelItemViews
import ca.thenightcrew.supervinebros.game_engine.SlideUtils.leftIDs
import ca.thenightcrew.supervinebros.game_engine.SlideUtils.rightIDs
import ca.thenightcrew.supervinebros.game_engine.Utils
import ca.thenightcrew.supervinebros.game_engine.Utils.Measurement.pixelToDP
import ca.thenightcrew.supervinebros.game_engine.level.components.items.LevelItem
import ca.thenightcrew.supervinebros.game_engine.level.components.items.LevelItemMap


/**
 * Recycler View Adapter used to display game level background repetitions and Level objects(Adapter
 * Pattern)
 */
class LevelAdapter  // Standard RecyclerView Adapter
/** See [RecyclerView] for more information of the standard methods of the recyclerview  */ // The differ is not needed in this current implementation of the RecyclerView however it can be
// be used in future versions of the game such as a game level that infinitely repeats
    : ListAdapter<LevelSegment, LevelAdapter.ViewHolder>(Utils.UnusedDiffer<LevelSegment>()) {
    // This recycler view differ between the last view holder versus other view holders. This allow
    // level vine to be shorter in in the last view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == 0) ViewHolder(
            layoutInflater.inflate(R.layout.fragment_slide_down_level, parent, false)
        ) else ViewHolder(
            layoutInflater.inflate(R.layout.fragment_slide_down_level_end, parent, false)
        )
    }

    // Helps determine that last view holder of the recyclerView
    override fun getItemViewType(position: Int): Int {
        return if (position != itemCount - 1) 0 else 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var itemsLeft: ArrayList<ImageView>? = null
        private var itemsRight: ArrayList<ImageView>? = null
        private var levelItemMap: LevelItemMap? = null

        /**
         * Helps bind level segments elements and represents them as view and the screen. Each segment
         * include LevelItems and backgrounds
         *
         * @param segment The segment data that includes background and LevelItems
         */
        fun bind(segment: LevelSegment?) {

            //Binding
            val vine = itemView.findViewById<ImageView>(R.id.slide_recycler_item_chain)
            val background = itemView.findViewById<ImageView>(R.id.slide_recycler_item_bg)
            val lastBackground = itemView.findViewById<ImageView>(R.id.slide_recycler_item_bg_last)

            // Set Vine and Background Resources
            vine.setBackgroundResource(segment!!.vineResourceID)
            val backgroundBitmap = segment.backgroundBitmap
            val lastBackgroundBitmap = segment.lastBackgroundBitmap
            setUpLevelItems(segment)
            background.setImageBitmap(backgroundBitmap)
            lastBackground.setImageBitmap(lastBackgroundBitmap)
        }

        /**
         * Populates LevelItem ImageView Holders with Sprite resources
         * @param segment
         */
        private fun setUpLevelItems(segment: LevelSegment?) {
            itemsLeft = getLevelItemViews(leftIDs, itemView)
            itemsRight = getLevelItemViews(rightIDs, itemView)
            levelItemMap = segment!!.levelItemMap
            if (levelItemMap != null) {
                addItemImageResources(itemsLeft!!, levelItemMap!!.sideLeft)
                addItemImageResources(itemsRight!!, levelItemMap!!.sideRight)
            } else {
                addItemImageResources(itemsLeft!!, null)
                addItemImageResources(itemsRight!!, null)
            }
        }

        /**
         * Populates a list of ImageViews with resource drawable data.
         * @param itemImageViews List of views to that can be use to display LevelItems
         * @param levelItems The LevelItem data that will help populate itemImageViews
         */
        private fun addItemImageResources(
            itemImageViews: ArrayList<ImageView>, levelItems: Array<LevelItem?>?
        ) {
            for (i in 0 until itemImageViews.size) {
                if (levelItems != null && levelItems[i] != null) {
                    val imageView = itemImageViews[i]
                    val levelItem = levelItems[i]
                    val layoutParams = imageView.layoutParams as ConstraintLayout.LayoutParams
                    layoutParams.width = pixelToDP(levelItem!!.size).toInt()
                    layoutParams.height = pixelToDP(levelItem.size).toInt()
                    imageView.layoutParams = layoutParams
                    imageView.setImageResource(levelItem.resourceIDs!![0])
                } else itemImageViews[i].setImageBitmap(null)
            }
        }
    }
}
