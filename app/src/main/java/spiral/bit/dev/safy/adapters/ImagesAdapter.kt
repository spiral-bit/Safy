package spiral.bit.dev.safy.adapters

import android.content.Context
import android.net.Uri
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skydoves.transformationlayout.TransformationLayout
import spiral.bit.dev.safy.R
import spiral.bit.dev.safy.data.Image
import spiral.bit.dev.safy.listeners.ImageListener
import java.io.File


class ImagesAdapter(listener: ImageListener) :
    RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    private var imageListener = listener
    private lateinit var context: Context
    private var previousTime = SystemClock.elapsedRealtime()

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var thumbnailImg: ImageView = itemView.findViewById(R.id.img_item_image)
        var transformationLayout: TransformationLayout =
            itemView.findViewById(R.id.transformationLayout)
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Image>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        context = parent.context
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.image_view,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = differ.currentList[position]
        val uri = Uri.fromFile(File(image.stringUri))
        Glide.with(context).asBitmap().load(uri).into(holder.thumbnailImg)
        holder.transformationLayout.transitionName = image.id.toString()
        holder.run {
            holder.itemView.setOnClickListener {
                val now = SystemClock.elapsedRealtime()
                if (now - previousTime >= holder.transformationLayout.duration) {
                    imageListener.onImgClicked(image, position, holder.transformationLayout)
                    previousTime = now
                }
            }
        }
    }


    override fun getItemCount(): Int = differ.currentList.size
}