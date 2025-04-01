package com.example.practicacalculadora.ui.viewModels

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicacalculadora.OperationType

class MainViewModel: ViewModel() {
    private var _result: MutableLiveData<String> = MutableLiveData("")
    var result: LiveData<String> = _result
    private var _show_error: MutableLiveData<Boolean> = MutableLiveData(false)
    var show_error: LiveData<Boolean> = _show_error
    private var currentOperation: OperationType = OperationType.NONE
    private var prevNumber: Int = 0
    private var resultado_memoria: Int = 0

    fun addNumber(num: Int) {
        if (_result.value?.isEmpty() == true || _result.value == "0"){
            _result.value = num.toString()
        } else {
            _result.value += num
        }
        //reloadScreen()
    }

    fun clearEverything() {
        prevNumber = 0
        currentOperation = OperationType.NONE
        _result.value = ""
        //reloadScreen()
    }

    fun clearOne() {
        if (_result.value?.isEmpty() == true) {
            return
        }
        _result.value = _result.value?.dropLast(1)
        //reloadScreen()
    }

    fun calculateResult() {
        var secondNumber = 0
        if (_result.value?.isNotEmpty() == true) {
            secondNumber = _result.value?.toInt()!!
        }
        val operationResult = when (currentOperation) {
            OperationType.ADDITION -> prevNumber + secondNumber
            OperationType.SUBTRACTION -> prevNumber - secondNumber
            OperationType.MULTIPLICATION -> prevNumber * secondNumber
            OperationType.DIVISION -> {
                if (secondNumber != 0) {
                    prevNumber / secondNumber
                } else {
                    _show_error.value = tr
                    return
                }
            }

            OperationType.NONE -> return
        }
        _result.value = operationResult.toString()
        currentOperation = OperationType.NONE
        prevNumber = 0
        //reloadScreen()
    }

    fun startOperation(operation: OperationType) {
        currentOperation = operation
        prevNumber = _result.value?.toInt()!!
        _result.value = ""
        //reloadScreen()
    }

    fun restarMemoria() {
        if (_result.value?.isNotEmpty() == true){
            resultado_memoria -= _result.value?.toInt()!!
            _result.value = ""
            //Toast.makeText(this,"Se resto a la memoria",Toast.LENGTH_SHORT).show()
            //reloadScreen()
        }
    }

    fun sumarMemoria() {
        if (_result.value?.isNotEmpty() == true){
            resultado_memoria += _result.value?.toInt()!!
            _result.value = ""
            //Toast.makeText(this,"Se sumo a la memoria",Toast.LENGTH_SHORT).show()
            //reloadScreen()
        }
    }

    fun extrarMemoria() {
        _result.value = resultado_memoria.toString()
        //reloadScreen()
    }

    fun limpiarMemoria() {
        resultado_memoria = 0
        //reloadScreen()
    }
}