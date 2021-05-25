package com.example.kotlinchannelsforevents.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    sealed class Events {
        data class ResultOne(val dataOne: String) : Events()
        data class ResultTwo(val dataTwo: Int) : Events()
        data class ResultThree(val dataThree: Boolean) : Events()
        data class ErrorForOne(val errorMessage: String) : Events()
        data class ErrorForTwo(val errorMessageTwo: String) : Events()
        data class ErrorForThree(val errorMessageThree: String) : Events()
    }

    private val eventChannel = Channel<Events>()
    val eventFlow = eventChannel.receiveAsFlow()

    fun getDataForFragmentOne() = viewModelScope.launch {
        eventChannel.send(Events.ResultOne("Hello One How are You"))
    }

    fun getDataForFragmentTwo() = viewModelScope.launch {
        Log.d(TAG, "In ViewModel scope Coroutine sending data to Fragment two")
        eventChannel.send(Events.ResultTwo(100))
    }

    fun getDataForFragmentThree() = viewModelScope.launch {
        eventChannel.send(Events.ResultThree(true))
    }

    fun getFailedRequestForFragmentOne(callingFragmentName: String) = viewModelScope.launch {
        eventChannel.send(Events.ErrorForOne("Sorry $callingFragmentName your network request failed!"))
    }

    fun getFailedRequestForFragmentTwo(callingFragmentName: String) = viewModelScope.launch {
        eventChannel.send(Events.ErrorForTwo("Sorry $callingFragmentName your network request failed!"))
    }

    fun getFailedRequestForFragmentThree(callingFragmentName: String) = viewModelScope.launch {
        eventChannel.send(Events.ErrorForThree("Sorry $callingFragmentName your network request failed!"))
    }


}