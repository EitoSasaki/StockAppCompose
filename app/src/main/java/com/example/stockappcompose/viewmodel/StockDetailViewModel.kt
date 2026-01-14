package com.example.stockappcompose.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockappcompose.data.db.Stock
import com.example.stockappcompose.orZero
import com.example.stockappcompose.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
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
        viewModelScope.launch {
            updateImage(_selectedImage.value).take(1).collect {
                onUpdateImage()
            }
        }
    }

    fun onClickDeleteImage() {
        viewModelScope.launch {
            updateImage(null).take(1).collect {
                _selectedImage.value = null
            }
        }
    }

    // TODO: 画像の更新の実装
    private fun updateImage(imageUri: Uri?): Flow<Unit> = flowOf(Unit)
}