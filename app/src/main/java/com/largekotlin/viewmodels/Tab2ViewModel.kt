package com.largekotlin.viewmodels

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.largekotlin.data.RetrofitClient
import com.largekotlin.util.MessageProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.NullPointerException
import java.util.Optional
import java.util.concurrent.TimeUnit
import kotlin.math.cos
import kotlin.random.Random

class Tab2ViewModel : ViewModel() {
    private val handler = android.os.Handler(Looper.getMainLooper())
    private val _txtInput = MutableLiveData<String>()
    val txtInput = _txtInput as LiveData<String>

    val txtColor = MutableLiveData<Int>()

    init {   }

    fun makeTimerObservable() {
        this._txtInput.value="Start Rxjava Observable.Interval"

        val timer = Observable.interval(1000L,TimeUnit.MILLISECONDS)

        val result = timer
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(){
                txtColor.value=rgbToInt(
                    Random.nextInt(255),
                    Random.nextInt(255),
                    Random.nextInt(255))

                this.addStringRow(it.toString())
            }

        handler.postDelayed({ result.dispose() }, DISPOSE_TIME_MILLIS)
    }

    private fun rgbToInt(r: Int, g: Int, b: Int): Int {
        return (255 and 0xff shl 24
                or (r and 0xff shl 16)
                or (g and 0xff shl 8)
                or (b and 0xff))
    }

    fun makeStandardObservable() {
        this._txtInput.value="Start Rxjava Observable.Just"

        // Rx-цепочка
        var observableA = Observable
            .just(0, PI / 2, PI, 6.25)
            .filter { it.toDouble() < 6.0 }
            .map { it -> cos(it.toDouble()) }
            .subscribe(
                { addStringRow("cos() = $it") },
                { error -> addStringRow("$error") },
                { addStringRow("Task completed\n") })
    }

    fun StandardRetrofit(){
        val quote = RetrofitClient().doRequest(object :MessageProvider{
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
