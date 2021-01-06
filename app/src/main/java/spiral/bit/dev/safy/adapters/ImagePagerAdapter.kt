package spiral.bit.dev.safy.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import spiral.bit.dev.safy.data.Image

class ImagePagerAdapter(
    var context: Context,
    var listOfImages: ArrayList<Image>
) : PagerAdapter() {

    override fun getCount(): Int {
        return listOfImages.size
    }

    override fun isViewFromObject(view: View, anyObject: Any): Boolean {
        return view == anyObject
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = PhotoView(context)
        Glide.with(context).load(listOfImages[position].stringUri)
            .into(imageView)
        container.addView(imageView, 0)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, anyObject: Any) {
        container.removeView(anyObject as ImageView)
    }
}