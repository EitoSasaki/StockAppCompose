package com.example.stockappcompose.ui.activity

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import com.example.stockappcompose.helper.ImagePickerHelper
import com.example.stockappcompose.ui.layout.StockApp
import com.example.stockappcompose.ui.theme.StockAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        var instance: MainActivity? = null
    }

    @Inject lateinit var imagePickerHelper: ImagePickerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        lifecycle.addObserver(imagePickerHelper)

        // TODO: 詳細画面に実装を移設する
        val requestPermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            requestPermissions.launch(arrayOf(READ_MEDIA_IMAGES, READ_MEDIA_VISUAL_USER_SELECTED))
        } else {
            requestPermissions.launch(arrayOf(READ_EXTERNAL_STORAGE))
        }

        enableEdgeToEdge()
        setContent {
            StockAppComposeTheme {
                StockApp()
            }
        }
    }
}