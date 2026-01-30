package com.example.stockappcompose.ui.layout.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.stockappcompose.R
import com.example.stockappcompose.data.ui.DialogActionType
import com.example.stockappcompose.data.ui.DialogType
import com.example.stockappcompose.data.ui.Screen
import com.example.stockappcompose.viewmodel.base.BaseViewModel

@Composable
fun Template(
    screen: Screen, // ヘッダーなどに画面名を表示したい場合に利用
    body: @Composable () -> Unit,
    viewModel: BaseViewModel,
) {
    val message = viewModel.messageType.collectAsState().value
    val formatArgs = viewModel.formatArgs.collectAsState().value

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
    ) {
        if (message != null) {
            when (message.dialogType) {
                DialogType.Info -> {
                    CommonSingleButtonDialog(
                        message = stringResource(id = message.messageId, *formatArgs.toTypedArray()),
                        btnText = R.string.button_ok,
                        onClickButton = { viewModel.setDialogActionType(DialogActionType.Ok) },
                        onDismiss = {},
                    )
                }
                DialogType.Error -> {
                    SingleButtonErrorDialog(
                        message = stringResource(id = message.messageId, *formatArgs.toTypedArray()),
                        code = message.code,
                        btnText = R.string.button_ok,
                        onClickButton = { viewModel.setDialogActionType(DialogActionType.Ok) },
                        onDismiss = {},
                    )
                }
                else -> {
                    // TODO: ダイアログ表示のケースが増えたら追加
                }
            }
        }
        body()
    }
}