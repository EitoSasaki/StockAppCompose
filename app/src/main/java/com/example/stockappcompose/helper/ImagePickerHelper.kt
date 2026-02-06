package com.example.stockappcompose.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.stockappcompose.data.error.base.BaseError
import com.example.stockappcompose.ui.activity.MainActivity
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagePickerHelper @Inject constructor() : DefaultLifecycleObserver {

    lateinit var getContent: ActivityResultLauncher<String>
    var scope: CoroutineScope? = CoroutineScope(Job() + Dispatchers.Main)
    private val _selectedUri = MutableSharedFlow<Uri?>(0)

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        MainActivity.instance?.activityResultRegistry?.let { registry ->
            getContent = registry.register(
                "key",
                owner,
                GetContent()
            ) { uri ->
                scope?.launch {
                    _selectedUri.emit(uri)
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<Result<Uri?, BaseError>> {
        getContent.launch("image/*")
        return _selectedUri.flatMapConcat {
            flowOf(Ok(it))
        }
    }
}

class GetContent : ActivityResultContract<String, Uri?>() {
    @CallSuper
    override fun createIntent(context: Context, input: String): Intent {
        return Intent(Intent.ACTION_PICK).setType(input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return intent.takeIf { resultCode == Activity.RESULT_OK }?.data
    }
}