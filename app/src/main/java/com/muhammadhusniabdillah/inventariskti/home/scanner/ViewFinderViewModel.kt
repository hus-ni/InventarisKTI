package com.muhammadhusniabdillah.inventariskti.home.scanner

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import uk.co.brightec.kbarcode.Barcode


class ViewFinderViewModel( application: Application
) : AndroidViewModel(application) {

    private val _barcode = MediatorLiveData<Resources<Barcode>>()
    val barcode: LiveData<Resources<Barcode>>
    get() = _barcode

    fun setData(data: LiveData<Barcode>) {
        _barcode.addSource(data) { barcode ->
            _barcode.value = Resources.Loading(barcode)
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    _barcode.value = Resources.Success(barcode)
                },
                DELAY
            )
        }
    }

    companion object {

        private const val DELAY = 3000L
    }
}