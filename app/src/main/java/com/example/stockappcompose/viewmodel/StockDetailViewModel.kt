package com.example.stockappcompose.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockappcompose.data.db.Stock
import com.example.stockappcompose.orZero
import com.example.stockappcompose.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockDetailViewModel @Inject constructor(
    private val stockRepository: StockRepository
) : ViewModel() {

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
            stockRepository.getOne(id.orZero()).take(1).collect { stock ->
                _stock.value = stock
                _selectedImage.value = stock?.imageUri?.let { Uri.parse(it) }
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
            stockRepository.updateStock(stock.copy(imageUri = imageUri)).take(1).collect {
                onUpdateImage()
            }
        }
    }

    fun onClickDeleteImage() {
        _selectedImage.value = null
    }
}