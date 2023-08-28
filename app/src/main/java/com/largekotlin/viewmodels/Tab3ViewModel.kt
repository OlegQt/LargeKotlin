package com.largekotlin.viewmodels

import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class Tab3ViewModel : ViewModel() {
    private val handler = android.os.Handler(Looper.getMainLooper())

    private var startTime: Long = 0
    private var durationMillis: Long = 0

    val dialogAlertMessage = MutableLiveData<String>()

    private val _threadTxt = MutableLiveData<String>()
    val threadTxt = _threadTxt as MutableLiveData

    private val searchText = MutableLiveData<String>()
    private var searchTextRunnable:Runnable? = null
    private var searchTextCoroutine:Job? = null

    init {
        threadTxt.value = " "
    }

    fun coroutinesLaunch() {
        val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            dialogAlertMessage.value = "ErrorHandler message = " + throwable.message.toString()
        }
        val dispatcher = Dispatchers.Main
        val coroutineName = CoroutineName("MyCoroutine")

        val job = viewModelScope.launch(dispatcher.plus(errorHandler).plus(coroutineName)) {
            // Start article
            threadTxt.value = "Start Coroutines"

            addTextInCoroutines(coroutineContext[CoroutineName].toString())

            // Factorial
            var factorialResult = 1L
            for (i in 1 until 20) {
                factorialResult *= i
                addTextInCoroutines("Result (n $i) = $factorialResult")
                if (i==14){
                    val temporalRes = withContext(Dispatchers.Main){
                        dialogAlertMessage.value = "WithContext fun"
                    }
                    addTextInCoroutines(temporalRes.toString())
                }
            }
        }

    }

    private suspend fun addTextInCoroutines(text: String) {
        delay(SLEEP_CONST_MILLIS)

        threadTxt.value = StringBuilder().apply {
            append(threadTxt.value)
            append("\n$text")
        }.toString()
    }

    fun startLongFun() {
        threadTxt.value = ""

        // Set startTime
        startTime = System.currentTimeMillis()

        // Main Task
        (0..10).forEach {
            Thread { longOperation(it) }.start()
        }
    }

    private fun longOperation(number: Int) {
        Thread.sleep(SLEEP_CONST_MILLIS)
        handler.post {

            // Расчет времени выполнения от старта всего цикла, а не конкретного потока
            durationMillis = System.currentTimeMillis() - startTime


            threadTxt.value = threadTxt.value + with(StringBuilder()) {
                append("$number ")
                append("( duration: $durationMillis) \n")
            }.toString()
        }
    }

    fun debounceSearchText(text: String){
        if (searchTextRunnable==null){
            searchTextRunnable = Runnable {
                dialogAlertMessage.value = "SearchRunnable (${searchText.value}) after 2 seconds"
            }
        }

        if (!text.equals(searchText.value)){
            searchText.value = text
            handler.removeCallbacks(searchTextRunnable!!,null)
            handler.postDelayed(searchTextRunnable!!,2000L)

        }
    }

    fun debounceSearchTextCoroutine(changedTxt:String){
        if (changedTxt != searchText.value)      {
            searchTextCoroutine?.cancel()
            searchTextCoroutine = viewModelScope.launch {
                delay(2000L)
                searchText.value = changedTxt
                dialogAlertMessage.value = "SearchDebounce with coroutines = $changedTxt"
            }
        }
    }

    companion object {
        const val SLEEP_CONST_MILLIS = 100L

    }
}