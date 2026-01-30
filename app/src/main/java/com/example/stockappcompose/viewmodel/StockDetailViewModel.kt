package com.example.stockappcompose.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockappcompose.data.db.Stock
import com.example.stockappcompose.extension.orZero
import com.example.stockappcompose.repository.StockRepository
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.core.net.toUri
import com.example.stockappcompose.viewmodel.base.BaseViewModel

@HiltViewModel
class StockDetailViewModel @Inject constructor(
    private val stockRepository: StockRepository
) : BaseViewModel() {

    private val _stock = MutableStateFlow<Stock?>(null)
    private val _selectedImage = MutableStateFlow<Uri?>(null)

    private var id: Int? = null

    val stock: StateFlow<Stock?> = _stock

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
                }
            }
        }
    }

    fun onClickDeleteImage() {
        _selectedImage.value = null
    }
}