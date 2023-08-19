package com.largekotlin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Tab2ViewModel : ViewModel() {
    private var _timer = MutableLiveData<Long>()
    val timer get() = _timer as LiveData<Long>
}