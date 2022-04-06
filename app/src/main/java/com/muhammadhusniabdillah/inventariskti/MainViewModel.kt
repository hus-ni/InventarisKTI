package com.muhammadhusniabdillah.inventariskti

import androidx.lifecycle.*
import com.muhammadhusniabdillah.inventariskti.home.TableHome
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class MainViewModel(private val repo: MainRepository) : ViewModel() {

    val inventoryDataLive: LiveData<MutableList<TableHome>> = repo.inventoryData.asLiveData()

    fun createData(data: TableHome) = GlobalScope.launch {
        repo.createData(data)
    }
}

@DelicateCoroutinesApi
class ViewModelFactory(private val repo: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}