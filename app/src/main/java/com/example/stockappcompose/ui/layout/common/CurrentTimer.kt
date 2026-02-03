package com.example.stockappcompose.ui.layout.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.stockappcompose.enumeration.common.DateFormat
import com.example.stockappcompose.extension.format
import kotlinx.coroutines.delay
import java.time.LocalDateTime

@Composable
fun CurrentTimer(
    modifier: Modifier = Modifier,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var canRunTimer by remember { mutableStateOf(false) }
    var currentTime: LocalDateTime by remember { mutableStateOf(LocalDateTime.now()) }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                canRunTimer = true
            } else if (event == Lifecycle.Event.ON_STOP) {
                canRunTimer = false
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(canRunTimer) {
        if (canRunTimer) {
            while (true) {
                currentTime = LocalDateTime.now()
                delay(1000L)
            }
        }
    }

    CommonMiddleLabel(
        modifier = modifier,
        text = currentTime.format(DateFormat.HHmmss.format),
    )
}