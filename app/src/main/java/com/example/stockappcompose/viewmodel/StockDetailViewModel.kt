package com.example.stockappcompose.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.stockappcompose.Stock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StockDetailViewModel(stock: Stock) : ViewModel() {

    private val _stock = MutableStateFlow<Stock?>(null)
    private val _selectedImage = MutableStateFlow<Uri?>(null)

    val stock: StateFlow<Stock?> = _stock
    val selectedImage: StateFlow<Uri?> = _selectedImage

    init {
        _stock.value = stock
        _selectedImage.value = stock.imageUri?.let { Uri.parse(it) }
    }

    fun onSelectImage(uri: Uri?) {
        _selectedImage.value = uri
    }
}