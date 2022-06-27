package com.example.jpmcnycschools.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jpmcnycschools.UIState
import com.example.jpmcnycschools.api.SchoolRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SchoolViewModel @Inject constructor(
    private val repository: SchoolRepository
): ViewModel() {

    private val _schoolResponse = MutableLiveData<UIState>()
    val schoolResponse: MutableLiveData<UIState> get() = _schoolResponse

    private val _schoolSatResponse = MutableLiveData<UIState>()
    val schoolSatResponse: MutableLiveData<UIState> get() = _schoolSatResponse

    fun getSchoolList() {
        viewModelScope.launch {
            repository.getSchools().collect{
                _schoolResponse.postValue(it)
            }
        }
    }

    fun getSatScores() {
        viewModelScope.launch {
            repository.getScores().collect{
                _schoolSatResponse.postValue(it)
            }
        }
    }

    /*
        Using these set functions to start the opening fragments in the loading state.
        This way they the api is only called when the fragment is first opened.
     */
    fun setLoadingState() { _schoolResponse.value = UIState.Loading }

    fun setSchoolDetails() { _schoolSatResponse.value = UIState.Loading }
}