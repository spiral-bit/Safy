package spiral.bit.dev.safy.views

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.viewpager.widget.ViewPager
import com.skydoves.transformationlayout.TransformationAppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import spiral.bit.dev.safy.R
import spiral.bit.dev.safy.adapters.ImagePagerAdapter
import spiral.bit.dev.safy.data.Image
import spiral.bit.dev.safy.viewmodels.ImageViewModel

@AndroidEntryPoint
class ImageActivity : TransformationAppCompatActivity() {

    private lateinit var imagePagerAdapter: ImagePagerAdapter
    private lateinit var listOfUris: ArrayList<Image>
    private lateinit var viewPager: ViewPager
    private val imageViewModel: ImageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        listOfUris = intent?.getParcelableArrayListExtra<Image>("stringUris") as ArrayList<Image>
        val position = intent.getIntExtra("position", 0)
        viewPager = findViewById(R.id.view_pager_images)
        imagePagerAdapter = ImagePagerAdapter(this, listOfImages = listOfUris)
        viewPager.adapter = imagePagerAdapter
        viewPager.setCurrentItem(position, true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.image_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_item -> {
                showDeleteImageDialog(listOfUris[viewPager.currentItem])
            }
            R.id.rename_item -> {
                //showRenameDialog()
            }
            R.id.slide_show_item -> {
                //
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDeleteImageDialog(image: Image) {
        val builder = AlertDialog.Builder(this)
        val view: View = LayoutInflater.from(this)
            .inflate(
                R.layout.dialog_delete_img,
                findViewById(R.id.layout_delete_img_container)
            )
        builder.setView(view)
        val dialogDeleteImg = builder.create()
        dialogDeleteImg.window?.setBackgroundDrawable(ColorDrawable(0))
        view.findViewById<TextView>(R.id.text_add)
            .setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    imageViewModel.deleteImg(image)
                }
                dialogDeleteImg.dismiss()
            }
        view.findViewById<TextView>(R.id.text_cancel)
            .setOnClickListener { dialogDeleteImg.dismiss() }
        dialogDeleteImg.show()
    }
}