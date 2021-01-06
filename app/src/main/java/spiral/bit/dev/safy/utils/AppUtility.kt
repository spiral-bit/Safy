package spiral.bit.dev.safy.utils

import android.Manifest
import android.content.Context
import pub.devrel.easypermissions.EasyPermissions

object AppUtility {
    fun hasGalleryPermissions(context: Context) =
        EasyPermissions.hasPermissions(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
}