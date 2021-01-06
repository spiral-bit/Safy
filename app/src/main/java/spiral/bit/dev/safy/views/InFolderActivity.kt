package spiral.bit.dev.safy.views

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import com.skydoves.transformationlayout.TransformationAppCompatActivity
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import dagger.hilt.android.AndroidEntryPoint
import spiral.bit.dev.safy.R
import spiral.bit.dev.safy.adapters.ImagesAdapter
import spiral.bit.dev.safy.data.Image
import spiral.bit.dev.safy.databinding.ActivityInFolderBinding
import spiral.bit.dev.safy.listeners.ImageListener
import spiral.bit.dev.safy.utils.showAddFolderDialog
import spiral.bit.dev.safy.viewmodels.FolderViewModel
import spiral.bit.dev.safy.viewmodels.ImageViewModel

@AndroidEntryPoint
class InFolderActivity : TransformationAppCompatActivity(), ImageListener {

    private lateinit var paletteView: ImageView
    private lateinit var iconAddImage: ImageView
    private lateinit var inFolderBinding: ActivityInFolderBinding
    private lateinit var imageAdapter: ImagesAdapter
    private var folderID: Int = 0
    private var folderName: String = ""
    private lateinit var listOfUris: ArrayList<Image>
    private lateinit var inFolderRv: RecyclerView
    private val imageViewModel: ImageViewModel by viewModels()
    private val folderViewModel: FolderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inFolderBinding = ActivityInFolderBinding.inflate(layoutInflater)
        setContentView(inFolderBinding.root)

        folderID = intent.getIntExtra("folderID", 0)
        folderName = intent.getStringExtra("folderName").toString()
        supportActionBar?.title = folderName
        imageViewModel.setParentId(folderID)
        listOfUris = arrayListOf()

        imageViewModel.imagesLiveData.observe(this, {
            it.forEach { img -> listOfUris.add(img) }
            imageAdapter.submitList(it)
        })

        imageViewModel.imageBackLiveData.observe(this, {
            if (it != null) {
                Glide.with(this@InFolderActivity).load(it.stringUri)
                    .into(paletteView)
            }
        })

        initViews()
        initPaletteView()
        setUpRecycler()

        iconAddImage.setOnClickListener {
            ImagePicker.create(this)
                .returnMode(ReturnMode.NONE)
                .folderMode(true)
                .toolbarFolderTitle(getString(R.string.add_to_safe_label))
                .toolbarImageTitle(getString(R.string.select_imgs_label))
                .toolbarArrowColor(Color.WHITE)
                .limit(200)
                .showCamera(true)
                .imageDirectory("Camera")
                .start()
        }
    }

    private fun setUpRecycler() {
        imageAdapter = ImagesAdapter(this)
        inFolderRv.apply {
            adapter = imageAdapter
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
        }
    }

    private fun initViews() {
        iconAddImage = inFolderBinding.iconAddImgMain
        paletteView = inFolderBinding.inFolderPaletteView
        inFolderRv = inFolderBinding.inFolderImagesRecycler
    }

    private fun initPaletteView() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val receiveImages: MutableList<com.esafirm.imagepicker.model.Image>? =
                ImagePicker.getImages(data)
            val images = mutableListOf<Image>()
            receiveImages?.forEach {
                val myImg = Image(0, it.path, "", folderID)
                images.add(myImg)
            }
            imageViewModel.insertListOfImages(images)
            recreate()
        }
    }

    override fun onImgClicked(
        image: Image,
        position: Int,
        transformationLayout: TransformationLayout
    ) {
        val intent = Intent(this, ImageActivity::class.java)
        intent.putExtra("stringUris", listOfUris)
        intent.putExtra("position", position)
        TransformationCompat.startActivity(transformationLayout, intent)
    }

    override fun onImgLongClicked(image: Image, position: Int) {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.in_folder_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_item -> {
                showDeleteDialog()
            }
            R.id.rename_item -> {
                showRenameDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showRenameDialog() {
        showAddFolderDialog(folderViewModel, inFolderRv, true)
    }

    private fun showDeleteDialog() {

    }
}