package com.largekotlin.viewmodels

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Tab1ViewModel : ViewModel() {
    private var _timer = MutableLiveData<Long>()
    val timer get() = _timer as LiveData<Long>

    private val handler = android.os.Handler(Looper.getMainLooper())

    private var timerRunnable: Runnable

    init {
        _timer.value = 0

        timerRunnable = Runnable { timerBody() }

        startTimer()
    }

    private fun timerBody() {
        _timer.value = _timer.value?.plus(1)

        handler.postDelayed(
            timerRunnable, TIMER_DELAY_mills
        )
    }

    fun startTimer(){
        handler.post(timerRunnable)
    }

    fun stopTimer() {
        handler.removeCallbacks(timerRunnable)
    }

    fun startBusyThread(consumer:Consumable){
        val busyRunnable = Runnable {
            //TODO: Добавить код с длительным вычислением
            handler.post { consumer.consume(9999L) }
        }

        val busyThread = Thread(busyRunnable, BUSY_RUN)
        busyThread.start()
        busyThread.join(6000L)
    }

    companion object {
        const val TIMER_DELAY_mills = 24L
        const val BUSY_RUN = "BUSY_RUNNABLE"
    }

    fun interface Consumable{
        fun consume(dataLong:Long)
    }
}