package ca.thenightcrew.supervinebros.game_engine

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import ca.thenightcrew.supervinebros.R
import ca.thenightcrew.supervinebros.game_engine.Utils.Measurement.pixelToDP
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class Utils {
    // Multithreading
    object Threads {
        // For future networking
        private val threadNT: ExecutorService = Executors.newSingleThreadExecutor()
        private val threadBG: ExecutorService = Executors.newSingleThreadExecutor()
        private val threadDB: ExecutorService = Executors.newSingleThreadExecutor()

        private fun threadMain(context: Context) = ContextCompat.getMainExecutor(context);

        fun runOnBackgroundThread(runnable: Runnable?) {
            threadBG.execute(runnable)

        }

        fun runOnDBThread(action: () -> Unit) {
            threadDB.execute(action)
        }

        fun runOnMainThread(context: Context, action: () -> Unit) {
            threadMain(context).execute(action)
        }


        fun runOnNetworkThread(runnable: Runnable?) {
            threadNT.execute(runnable)
        }
    }


    object Measurement {
        fun pixelToDP(pixels: Int): Float {
            val density: Float = Resources.getSystem().displayMetrics.density
            return pixels * density
        }
    }

    class UnusedDiffer<T> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return false
        }
    }

    object ImageTools {
        fun createImageWithPorterDuff(
            image: Bitmap, filterImage: Bitmap, type: PorterDuff.Mode
        ): Bitmap {
            val result = Bitmap.createBitmap(image.width, image.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas()
            canvas.setBitmap(result)
            val paint = Paint()
            paint.isFilterBitmap = false
            canvas.drawBitmap(image, 0f, 0f, paint)
            paint.xfermode = PorterDuffXfermode(type)
            canvas.drawBitmap(filterImage, 0f, 0f, paint)
            paint.xfermode = null
            return result
        }

        fun cropBitmap(bitmap: Bitmap?, width: Int, height: Int): Bitmap {
            return Bitmap.createBitmap(bitmap!!, 0, 0, width, height)
        }

        fun createClippedImage(image: Bitmap, clipImage: Bitmap): Bitmap {
            return createImageWithPorterDuff(image, clipImage, PorterDuff.Mode.DST_IN)
        }

        fun createInversedClippedImage(image: Bitmap, clipImage: Bitmap): Bitmap {
            return createImageWithPorterDuff(image, clipImage, PorterDuff.Mode.DST_OUT)
        }
    }
}


/**
 * A utility class that helps with different set of problems in the game
 */
object SlideUtils {
    private const val LEVEL_SEGMENT_HEIGHT = 800
    private const val NOT_DEFINED_BACKGROUND = 0

    /** @return gets item row count used in the grid of LevelItems
     */
    const val levelItemRowCount = 4
    private var clipMap: Bitmap? = null

    /** @return  The height of the level segment in the recyclerView as density pixel
     */
    val levelSegmentHeight: Float
        get() = pixelToDP(LEVEL_SEGMENT_HEIGHT)

    /** @return  The height of the level segment row in the recyclerView as density pixel
     */
    val levelSegmentItemRowHeight: Float
        get() = pixelToDP(LEVEL_SEGMENT_HEIGHT) / 4

    /**
     * Generates a new bitmap with a triangle mask cutout
     * @param resources The android resource needed to get drawables
     * @param backgroundResourceId The id of the background resources
     * @return A masked bitmap with triangle cutouts
     */
    fun generateClippedImage(resources: Resources, backgroundResourceId: Int): Bitmap {
        if (backgroundResourceId == NOT_DEFINED_BACKGROUND) throw Error("Resource Not Found")
        val ogBitmap = BitmapFactory.decodeResource(resources, backgroundResourceId)
        return Utils.ImageTools.createClippedImage(
            ogBitmap, getClipMap(resources)
        )
    }

    /**
     * Generates a new bitmap with a triangle mask cutout showing the top portion of triangles
     * @param resources The android resource needed to get drawables
     * @param backgroundResourceId The id of the background resources
     * @return A masked bitmap with triangle cutouts showing the top portion
     */
    fun generateInversedClippedImage(resources: Resources, backgroundResourceId: Int): Bitmap {
        if (backgroundResourceId == NOT_DEFINED_BACKGROUND) throw Error("Resource Not Found")
        val ogBitmap = BitmapFactory.decodeResource(resources, backgroundResourceId)
        return Utils.ImageTools.createInversedClippedImage(
            ogBitmap, getClipMap(resources)
        )
    }

    /**
     * Generates a new bitmap from drawable resource
     * @param resources The android resource needed to get drawables
     * @param backgroundResourceId The id of the background resources
     * @return A new bitmap from drawable resource
     */
    fun generateImage(resources: Resources?, resourceId: Int): Bitmap {
        return if (resourceId == NOT_DEFINED_BACKGROUND) throw Error("Resource Not Found")
        else BitmapFactory.decodeResource(
            resources,
            resourceId
        )
    }

    /**
     * Generates a new bitmap used for masking
     * @param resources The android resource needed to get drawables
     * @return A bitmap representing the mask of other bitmaps
     */
    fun getClipMap(resources: Resources): Bitmap =
        clipMap ?: BitmapFactory.decodeResource(resources, R.drawable.slide_clip_map_500)
            .also { clipMap = it }


    /** @return Generates the left ids used for the gird of LevelItems in the LevelRecycler
     */
    val leftIDs: IntArray
        get() = intArrayOf(R.id.slide_l_1, R.id.slide_l_2, R.id.slide_l_3, R.id.slide_l_4)

    /** @return Generates the left ids used for the gird of LevelItems in the LevelRecycler
     */
    val rightIDs: IntArray
        get() = intArrayOf(R.id.slide_r_1, R.id.slide_r_2, R.id.slide_r_3, R.id.slide_r_4)

    /**
     * A helper function that returns a list of images views in a ViewHolder given by a list of Ids
     *
     * @param ids The ids of ImageViews inside the View Holder that need to bind
     * @param itemViewHolder The Holder View of all the images views
     * @return A list of ImagesViews (binded XML-Java) from the ViewHolder
     */
    fun getLevelItemViews(ids: IntArray, itemViewHolder: View): ArrayList<ImageView> {
        val views: ArrayList<ImageView> = ArrayList()
        for (id in ids) {
            views.add(itemViewHolder.findViewById(id) as ImageView)
        }
        return views
    }
}
