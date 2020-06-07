package com.sharad.googlekt.ui.MainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharad.googlekt.model.StateWise
import com.sharad.googlekt.repository.CovidRepository
import com.sharad.googlekt.utils.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class MainViewModel(private val repository: CovidRepository) : ViewModel() {

    private val _covidLiveData = MutableLiveData<State<StateWise>>()

    val covidLiveData: LiveData<State<StateWise>> = _covidLiveData

    fun getData() {
        viewModelScope.launch {
            repository.getData().collect {
                _covidLiveData.value = it
            }
        }
    }
}