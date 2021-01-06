package spiral.bit.dev.safy.views

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.navigation.NavigationView
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import spiral.bit.dev.safy.R
import spiral.bit.dev.safy.adapters.FoldersAdapter
import spiral.bit.dev.safy.data.Folder
import spiral.bit.dev.safy.databinding.ActivityHideBinding
import spiral.bit.dev.safy.listeners.FolderListener
import spiral.bit.dev.safy.listeners.SelectListener
import spiral.bit.dev.safy.utils.AppUtility
import spiral.bit.dev.safy.utils.showAddFolderDialog
import spiral.bit.dev.safy.utils.showDeleteFolderDialog
import spiral.bit.dev.safy.viewmodels.FolderViewModel
import spiral.bit.dev.safy.viewmodels.ImageViewModel


@AndroidEntryPoint
class HideActivity : AppCompatActivity(), FolderListener, SelectListener,
    EasyPermissions.PermissionCallbacks {

    private lateinit var hideBinding: ActivityHideBinding
    private lateinit var folderAdapter: FoldersAdapter
    private lateinit var foldersRecycler: RecyclerView
    private lateinit var drawer: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var dialogAddFolder: AlertDialog
    private val folderViewModel: FolderViewModel by viewModels()
    private val imageViewModel: ImageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideBinding = ActivityHideBinding.inflate(layoutInflater)
        setContentView(hideBinding.root)
        setUpNavDrawer()
        requestPermissions()

        foldersRecycler = hideBinding.foldersRecycler
        folderAdapter = FoldersAdapter(this, this)
        foldersRecycler.apply {
            adapter = folderAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
        }

        folderViewModel.foldersLiveData.observe(this, {
            folderAdapter.submitList(it)
        })
    }

    private fun setUpNavDrawer() {
        drawer = hideBinding.drawer
        navigationView = hideBinding.navView
        toggle = ActionBarDrawerToggle(
            this,
            drawer,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawer.addDrawerListener(toggle)
        navigationView.bringToFront()
        navigationView.requestFocus()
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.settings_item -> {
                    Toast.makeText(this@HideActivity, it.itemId.toString(), Toast.LENGTH_LONG)
                        .show()
                }
                R.id.browser_item -> {
                    startActivity(Intent(this@HideActivity, BrowserActivity::class.java))
                }
                R.id.safe_item -> {
                    Toast.makeText(this@HideActivity, it.itemId.toString(), Toast.LENGTH_LONG)
                        .show()
                }
                R.id.about_item -> {
                    Toast.makeText(this@HideActivity, it.itemId.toString(), Toast.LENGTH_LONG)
                        .show()
                }
                R.id.theme_item -> {
                    Toast.makeText(this@HideActivity, it.itemId.toString(), Toast.LENGTH_LONG)
                        .show()
                }
                R.id.trash_item -> {
                    Toast.makeText(this@HideActivity, it.itemId.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }
            true
        }
    }

    override fun onFolderClicked(
        folder: Folder,
        position: Int,
        transformationLayout: TransformationLayout
    ) {
        val intent = Intent(this, InFolderActivity::class.java)
        intent.putExtra("folderID", folder.id)
        intent.putExtra("folderName", folder.nameOfFolder)
        TransformationCompat.startActivity(transformationLayout, intent)
    }

    override fun onFolderLongClicked(folder: Folder, position: Int) {
        folder.id?.let { imageViewModel.setParentId(it) }
        imageViewModel.imagesLiveData.observe(this, {
            showDeleteFolderDialog(folderViewModel, imageViewModel, foldersRecycler, folder, it)
        })
    }

    override fun onBackPressed() = startActivity(
        Intent(this@HideActivity, CalcActivity::class.java)
    )

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) return true
        when (item.itemId) {
            R.id.add_folder_item -> {
                showAddFolderDialog(folderViewModel, drawer, false)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    private fun requestPermissions() {
        if (AppUtility.hasGalleryPermissions(this)) return
        EasyPermissions.requestPermissions(
            this,
            "Вам нужно принять разрешения, чтобы корректно работать с приложением!)",
            11,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onItemSelected(isSelected: Boolean) {
        Toast.makeText(this, "SELECTED!", Toast.LENGTH_LONG).show()
    }

    override fun onItemSelectedByOneTap(isSelected: Boolean) {

    }
}