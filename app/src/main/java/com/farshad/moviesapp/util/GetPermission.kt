package com.farshad.moviesapp.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import com.farshad.moviesapp.ui.MainActivity
import com.permissionx.guolindev.PermissionX

open class GetPermission {

     fun getPermission(
         activity:MainActivity,
         context : Context,
         permissions: String ,
         resultLauncher : ActivityResultLauncher<Intent> ,
         intent : Intent
     ) {
        PermissionX.init(activity)
            .permissions(permissions)
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    "Core fundamental are based on these permissions",
                    "OK",
                    "Cancel"
                )
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    "You need to allow necessary permissions in Settings manually",
                    "OK",
                    "Cancel"
                )
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    resultLauncher.launch(intent)

                } else {
                    Toast.makeText(
                        context,
                        "These permissions are denied: $deniedList",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}