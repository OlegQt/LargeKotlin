package com.largekotlin.viewmodels

import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.largekotlin.data.RetrofitClient
import com.largekotlin.util.MessageProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit
import kotlin.math.cos
import kotlin.random.Random

class Tab2ViewModel : ViewModel() {
    private val handler = android.os.Handler(Looper.getMainLooper())
    private val _txtInput = MutableLiveData<String>()

    val txtInput = _txtInput as LiveData<String>

    val rxTimer = MutableLiveData<Long>()
    private var timerDisposable: Disposable? = null

    val txtColor = MutableLiveData<Int>()

    init {}

    fun makeTimerObservable() {
        if(timerDisposable!=null) handler.post { timerDisposable?.dispose() }

        val timer = Observable.interval(100L, TimeUnit.MILLISECONDS)
        timerDisposable = timer
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe() {
                txtColor.value = rgbToInt(
                    Random.nextInt(255),
                    Random.nextInt(255),
                    Random.nextInt(255)
                )

                this.rxTimer.value = it
            }

    }

    private fun rgbToInt(r: Int, g: Int, b: Int): Int {
        return (255 and 0xff shl 24
                or (r and 0xff shl 16)
                or (g and 0xff shl 8)
                or (b and 0xff))
    }

    fun standardRetrofit() {
        val quote = RetrofitClient().doRequest(object : MessageProvider {
            override fun showMessage(message: String) {
                _txtInput.value = message
            }
        })
    }

    private fun addStringRow(stringRow: String) {
        _txtInput.value = _txtInput.value.plus("\n$stringRow")
    }

    companion object {
        const val PI = 3.14159265359
        const val DISPOSE_TIME_MILLIS = 14050L
    }
}
