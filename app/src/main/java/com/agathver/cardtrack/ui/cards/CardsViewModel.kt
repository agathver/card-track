package com.agathver.cardtrack.ui.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CardsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Detected cards will show here"
    }
    val text: LiveData<String> = _text
}