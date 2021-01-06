package spiral.bit.dev.safy.adapters

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.transformationlayout.TransformationLayout
import spiral.bit.dev.safy.R
import spiral.bit.dev.safy.data.Folder
import spiral.bit.dev.safy.listeners.FolderListener
import spiral.bit.dev.safy.listeners.SelectListener
import java.util.*

class FoldersAdapter(listener: FolderListener, selectListener: SelectListener) :
    RecyclerView.Adapter<FoldersAdapter.FolderViewHolder>() {

    private var folderListener = listener
    private var previousTime = SystemClock.elapsedRealtime()

    inner class FolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textFolderName: TextView = itemView.findViewById(R.id.txt_item_folder_name)
        var transformationLayout: TransformationLayout = itemView.findViewById(R.id.layout_folder)
    }

    val diffCallback = object : DiffUtil.ItemCallback<Folder>() {
        override fun areItemsTheSame(oldItem: Folder, newItem: Folder): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Folder, newItem: Folder): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Folder>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        return FolderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.folder_view,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        val folder = differ.currentList[position]
        holder.transformationLayout.transitionName = folder.nameOfFolder
        holder.transformationLayout.setOnClickListener {
            val now = SystemClock.elapsedRealtime()
            if (now - previousTime >= holder.transformationLayout.duration) {
                folderListener.onFolderClicked(folder, position, holder.transformationLayout)
                previousTime = now
            }
        }
        holder.transformationLayout.setOnLongClickListener {
            folderListener.onFolderLongClicked(folder, position)
            return@setOnLongClickListener true
        }
        holder.textFolderName.text = folder.nameOfFolder
    }

    override fun getItemCount(): Int = differ.currentList.size
}