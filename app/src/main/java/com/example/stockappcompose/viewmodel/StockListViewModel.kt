package com.example.stockappcompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockappcompose.Constants
import com.example.stockappcompose.flatMapSuccess
import com.example.stockappcompose.repository.StockRepository
import com.example.stockappcompose.ui.layout.common.StockListRowData
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockListViewModel @Inject constructor(
    private val stockRepository: StockRepository
) : ViewModel() {

    private val _list = MutableStateFlow<List<StockListRowData>>(emptyList())
    private val _amount = MutableStateFlow(0)

    val list: StateFlow<List<StockListRowData>> = _list
    val amount: StateFlow<Int> = _amount

    // 初期表示
    fun initView() {
        viewModelScope.launch {
            stockRepository.getStocks().take(1).collect { result ->
                result.onSuccess { stocks ->
                    _list.value = stocks.map { stock ->
                        StockListRowData(isChecked = false, stock = stock)
                    }
                }.onFailure {
                    print(it.cause)
                }
            }
        }
    }

    fun onChangeAmount(newValue: Int) {
        if (newValue <= Constants.STOCK_AMOUNT_MAX && newValue >= Constants.STOCK_AMOUNT_MIN) {
            _amount.value = newValue
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun onClickSubmit(comment: String) {
        viewModelScope.launch {
            stockRepository.insertStock(
                comment = comment.ifBlank { null },
                amount = _amount.value
            ).flatMapSuccess {
                stockRepository.getOne(it)
            }.take(1).collect { result ->
                result.onSuccess { newValue ->
                    newValue?.let {
                        _list.value = _list.value.toMutableList().also {
                            it.add(StockListRowData(isChecked = false, stock = newValue))
                        }
                    }
                }.onFailure {
                    print(it.cause)
                }
            }
        }
    }

    fun onChangeChecked(index: Int, isChecked: Boolean) {
        _list.value = _list.value.toMutableList().also { list ->
            list.getOrNull(index)?.let { row ->
                list[index] = row.copy(isChecked = isChecked)
            }
        }
    }

    fun onClickDelete(index: Int) {
        val stock = _list.value.toMutableList().getOrNull(index)?.stock ?: return
        viewModelScope.launch {
            stockRepository.deleteStock(stock).take(1).collect { result ->
                result.onSuccess {
                    _list.value = _list.value.toMutableList().also {
                        it.removeAt(index)
                    }
                }.onFailure {
                    print(it.cause)
                }
            }
        }
    }

    fun onClickClear() {
        viewModelScope.launch {
            stockRepository.deleteAll().take(1).collect { result ->
                result.onSuccess {
                    _list.value = _list.value.toMutableList().also {
                        it.clear()
                    }
                }.onFailure {
                    print(it.cause)
                }
            }
        }
    }
}