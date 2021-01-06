package spiral.bit.dev.safy.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import spiral.bit.dev.safy.R
import spiral.bit.dev.safy.data.Folder
import spiral.bit.dev.safy.data.Image
import spiral.bit.dev.safy.viewmodels.FolderViewModel
import spiral.bit.dev.safy.viewmodels.ImageViewModel

fun returnBitmap(originalImage: Bitmap, width: Int, height: Int): Bitmap? {
    val background = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val originalWidth = originalImage.width.toFloat()
    val originalHeight = originalImage.height.toFloat()
    val canvas = Canvas(background)
    val scale = width / originalWidth
    val xTranslation = 0.0f
    val yTranslation = (height - originalHeight * scale) / 2.0f
    val transformation = Matrix()
    transformation.postTranslate(xTranslation, yTranslation)
    transformation.preScale(scale, scale)
    val paint = Paint()
    paint.isFilterBitmap = true
    canvas.drawBitmap(originalImage, transformation, paint)
    return background
}

fun showAddFolderDialog(folderViewModel: FolderViewModel, viewArg: View, isRename: Boolean) {
    val builder = AlertDialog.Builder(viewArg.context)
    val view: View = LayoutInflater.from(viewArg.context)
        .inflate(
            R.layout.dialog_add_folder,
            viewArg.findViewById(R.id.layout_add_folder_container)
        )
    builder.setView(view)
    val dialogAddFolder = builder.create()
    dialogAddFolder.window?.setBackgroundDrawable(ColorDrawable(0))
    val etFolderName = view.findViewById<EditText>(R.id.input_folder_name)
    view.findViewById<TextView>(R.id.text_add)
        .setOnClickListener {
            val nameFolder: String = etFolderName.text.toString()
            if (isRename) {
                folderViewModel.updateFolder(Folder(nameFolder))
            } else {
                folderViewModel.insertFolder(Folder(nameFolder))
                dialogAddFolder.dismiss()
            }
        }
    view.findViewById<TextView>(R.id.text_cancel)
        .setOnClickListener { dialogAddFolder.dismiss() }
    dialogAddFolder.show()
}

fun showDeleteFolderDialog(
    folderViewModel: FolderViewModel, imageViewModel: ImageViewModel, viewArg: View, folder: Folder,
    list: List<Image>
) {
    val builder = AlertDialog.Builder(viewArg.context)
    val view: View = LayoutInflater.from(viewArg.context)
        .inflate(
            R.layout.dialog_delete_folder,
            viewArg.findViewById(R.id.layout_delete_folder_container)
        )
    builder.setView(view)
    val dialogDeleteFolder = builder.create()
    dialogDeleteFolder.window?.setBackgroundDrawable(ColorDrawable(0))
    view.findViewById<TextView>(R.id.text_add)
        .setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                    for (item in list) {
                        folder.id?.let { it1 -> imageViewModel.deleteAllImagesByFolderId(folderID = it1) }
                    }
                folderViewModel.deleteFolder(folder)
            }
            dialogDeleteFolder.dismiss()
        }
    view.findViewById<TextView>(R.id.text_cancel)
        .setOnClickListener { dialogDeleteFolder.dismiss() }
    dialogDeleteFolder.show()
}