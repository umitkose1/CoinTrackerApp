package com.demo.cointrackerapp.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.cointrackerapp.data.local.Alerts
import com.demo.cointrackerapp.data.local.LocalDataSource
import com.demo.cointrackerapp.data.repository.CoinGeckoServiceRepository
import com.demo.cointrackerapp.model.CoinHistoryResponse
import com.demo.cointrackerapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinHistoryViewModel @Inject constructor(
    private val repository: CoinGeckoServiceRepository
) : ViewModel() {

    private val _response: MutableLiveData<Resource<CoinHistoryResponse>> = MutableLiveData()
    val response: LiveData<Resource<CoinHistoryResponse>> = _response


    var allAlerts: LiveData<List<Alerts>> = repository.fetchAlertsItems()


    fun fetchCoinPrices(id:String) = viewModelScope.launch {

        _response.postValue(Resource(Resource.Status.LOADING, null, null))

        repository.getHistory(id).let {
            _response.postValue(Resource(Resource.Status.SUCCESS, it.data, null))
        }
    }

    fun deleteAlert(alerts: Alerts){
        viewModelScope.launch {
            repository.deleteAlerts(alerts)
        }
    }
}