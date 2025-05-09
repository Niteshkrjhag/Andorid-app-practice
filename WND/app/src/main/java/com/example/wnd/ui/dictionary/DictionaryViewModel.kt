package com.example.wnd.ui.dictionary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wnd.data.model.dictionaryModel.DictionaryResponse
import com.example.wnd.data.remote.weatherApi.NetworkResponse
import com.example.wnd.repository.DictionaryRepository
import kotlinx.coroutines.launch

class DictionaryViewModel(private val dictionaryRepository: DictionaryRepository): ViewModel() {
    private val _defination = MutableLiveData<NetworkResponse<DictionaryResponse>>()
    val defination:LiveData<NetworkResponse<DictionaryResponse>> = _defination

    fun getDefination(word: String){
        _defination.value = NetworkResponse.Loading
        viewModelScope.launch {
            try{
                val response = dictionaryRepository.getDefination(word)
                if(response.isSuccessful){
                    response.body()?.let{
                        _defination.value = NetworkResponse.Success(it)
                    }
                }else{
                    _defination.value = NetworkResponse.Failure("Response Unsuccessfull")
                }
            }catch (e: Exception){
                _defination.value = NetworkResponse.Failure("Unexpected Error : $e")
            }
        }
    }
}