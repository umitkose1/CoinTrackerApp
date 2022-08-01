package com.demo.cointrackerapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.cointrackerapp.data.local.Alerts
import com.demo.cointrackerapp.data.local.LocalDataSource
import com.demo.cointrackerapp.data.repository.CoinGeckoServiceRepository
import com.demo.cointrackerapp.model.BaseResponse
import com.demo.cointrackerapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val repository: CoinGeckoServiceRepository
) : ViewModel() {

    private val _response: MutableLiveData<Resource<BaseResponse>> = MutableLiveData()
    val response: LiveData<Resource<BaseResponse>> = _response

    val isInserting = MutableLiveData<Boolean>()


    fun fetchCoinPrices() = viewModelScope.launch {

        _response.postValue(Resource(Resource.Status.LOADING, null, null))

        repository.getPrices().let {
            _response.postValue(Resource(Resource.Status.SUCCESS, it.data, null))
        }

    }

    fun insertAlert(alerts: Alerts) {
        isInserting.postValue(true)
        viewModelScope.launch {
            repository.insertAlertsData(alerts)
            isInserting.postValue(false)
        }
    }

}