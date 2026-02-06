package com.example.stockappcompose.viewmodel

import android.net.Uri
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.example.stockappcompose.data.db.Stock
import com.example.stockappcompose.extension.orZero
import com.example.stockappcompose.helper.ImagePickerHelper
import com.example.stockappcompose.repository.StockRepository
import com.example.stockappcompose.ui.activity.MainActivity
import com.example.stockappcompose.viewmodel.base.BaseViewModel
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockDetailViewModel @Inject constructor(
    private val stockRepository: StockRepository,
    private val imagePickerHelper: ImagePickerHelper,
) : BaseViewModel() {

    private val _stock = MutableStateFlow<Stock?>(null)
    private val _selectedImage = MutableStateFlow<Uri?>(null)

    private var id: Int? = null

    val stock: StateFlow<Stock?> = _stock
    val selectedImage: StateFlow<Uri?> = _selectedImage

    fun setId(id: Int) {
        this.id = id
    }

    fun initView() {
        if (id == null) return
        viewModelScope.launch {
            stockRepository.getOne(id.orZero()).take(1).collect { result ->
                result.onSuccess { stock ->
                    _stock.value = stock
                    _selectedImage.value = stock?.imageUri?.toUri()
                }.onFailure {
                    print(it.cause)
                    showMessage(
                        messageType = it.messageType
                    ).first()
                }
            }
        }
    }

    fun onSelectImage(uri: Uri?) {
        _selectedImage.value = uri
    }

    fun onClickSaveImage(onUpdateImage: () -> Unit) {
        val stock = _stock.value ?: return
        val imageUri = _selectedImage.value?.path
        viewModelScope.launch {
            stockRepository.updateStock(
                stock.copy(imageUri = imageUri)
            ).take(1).collect { result ->
                result.onSuccess {
                    onUpdateImage()
                }.onFailure {
                    print(it.cause)
                    showMessage(
                        messageType = it.messageType
                    ).first()
                }
            }
        }
    }

    fun onClickOpenImage() {
        viewModelScope.launch {
            imagePickerHelper().take(1).collect { result ->
                result.onSuccess {
                    _selectedImage.value = it
                }.onFailure {
                    print(it.cause)
                    showMessage(
                        messageType = it.messageType
                    ).first()
                }
            }
        }
    }

    fun onClickDeleteImage() {
        _selectedImage.value = null
    }
}