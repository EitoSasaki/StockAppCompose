package com.example.stockappcompose.viewmodel.base

import androidx.lifecycle.ViewModel
import com.example.stockappcompose.data.error.base.BaseError
import com.example.stockappcompose.data.ui.DialogActionType
import com.example.stockappcompose.data.ui.MessageType
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take

abstract class BaseViewModel : ViewModel() {

    private val _messageType = MutableStateFlow<MessageType?>(null)
    private val _formatArgs = MutableStateFlow<List<Any>>(emptyList())
    private val _dialogActionType = MutableStateFlow(DialogActionType.None)
    val messageType: StateFlow<MessageType?> = _messageType
    val formatArgs: StateFlow<List<Any>> = _formatArgs

    @OptIn(ExperimentalCoroutinesApi::class)
    fun showMessage(
        messageType: MessageType?,
        formatArgs: List<Any> = emptyList(),
    ): Flow<Result<DialogActionType, BaseError>> {
        _messageType.value = messageType ?: MessageType.UnknownError
        _formatArgs.value = formatArgs
        return waitDialogAction().take(1)
    }

    fun setDialogActionType(action: DialogActionType) {
        _dialogActionType.value = action
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun waitDialogAction(): Flow<Result<DialogActionType, BaseError>> =
         _dialogActionType.flatMapConcat { action ->
            if (action != DialogActionType.None) {
                _messageType.value = null
                _formatArgs.value = emptyList()
                _dialogActionType.value = DialogActionType.None
                flowOf(Ok(action))
            } else {
                delay(1000)
                waitDialogAction()
            }
        }
}