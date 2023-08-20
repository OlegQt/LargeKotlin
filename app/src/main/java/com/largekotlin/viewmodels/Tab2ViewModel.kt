package com.largekotlin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit
import kotlin.math.cos
import kotlin.random.Random

class Tab2ViewModel : ViewModel() {
    private val _txtInput = MutableLiveData<String>()
    val txtInput = _txtInput as LiveData<String>

    val txtColor = MutableLiveData<Int>()

    init {
        val subscribe = Flowable.just("Flowable string", "Second str from Flowable")
            .subscribe() { addStringRow(it) }


        // Rx-цепочка
        var observable = Observable
            .just(0, PI / 2, PI, 6.25)
            .filter { it.toDouble() < 6.0 }
            .map { it -> cos(it.toDouble()) }
            .subscribe(
                { addStringRow("cos() = $it") },
                { error -> addStringRow("$error") },
                { addStringRow("\nTask completed") })

        // Rx chain with timer
        observable = Observable.interval(1000L, TimeUnit.MILLISECONDS)
            .subscribe() {
                txtColor.postValue(
                    255 and 0xff shl 24
                            or (Random.nextInt(255) and 0xff shl 16)
                            or (Random.nextInt(255) and 0xff shl 8)
                            or (Random.nextInt(255) and 0xff)
                )
            }
    }


    private fun addStringRow(stringRow: String) {
        _txtInput.value = _txtInput.value.plus("\n$stringRow")
    }

    companion object {
        const val PI = 3.14159265359
    }
}
