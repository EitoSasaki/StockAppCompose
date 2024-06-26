package com.example.stockappcompose.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.stockappcompose.Stock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StockDetailViewModel(index: Int, stock: Stock) : ViewModel() {

    private val _index = MutableStateFlow(0)
    private val _stock = MutableStateFlow<Stock?>(null)
    private val _selectedImage = MutableStateFlow<Uri?>(null)

    val index: StateFlow<Int> = _index
    val stock: StateFlow<Stock?> = _stock
    val selectedImage: StateFlow<Uri?> = _selectedImage

    init {
        _index.value = index
        _stock.value = stock
        _selectedImage.value = stock.imageUri?.let { Uri.parse(it) }
    }

    fun onSelectImage(uri: Uri?) {
        _selectedImage.value = uri
    }

    fun onClickDeleteImage() {
        _selectedImage.value = null
    }
}