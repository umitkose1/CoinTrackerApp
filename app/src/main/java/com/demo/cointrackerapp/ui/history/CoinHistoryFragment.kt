package com.demo.cointrackerapp.ui.history

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.cointrackerapp.R
import com.demo.cointrackerapp.adapter.CoinAlertsAdapter
import com.demo.cointrackerapp.adapter.CoinHistoryAdapter
import com.demo.cointrackerapp.databinding.FragmentCoinhistoryBinding
import com.demo.cointrackerapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import timber.log.Timber

@AndroidEntryPoint
@WithFragmentBindings
class CoinHistoryFragment : Fragment(R.layout.fragment_coinhistory) {

    private val coinHistoryViewModel: CoinHistoryViewModel by viewModels()
    private val args: CoinHistoryFragmentArgs by navArgs()
    private lateinit var adapter: CoinHistoryAdapter
    private lateinit var alertsAdapter: CoinAlertsAdapter


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCoinhistoryBinding.bind(view)


        binding.rvListHistory.layoutManager = LinearLayoutManager(context)
        binding.rvListHistory.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rvListAlert.layoutManager = LinearLayoutManager(context)
        binding.rvListAlert.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        coinHistoryViewModel.fetchCoinPrices(args.id.toString())


        coinHistoryViewModel.response.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Timber.d("Result is Loading.")
                }

                Resource.Status.SUCCESS -> {
                    adapter = it.data?.let { it1 -> CoinHistoryAdapter(it1) }!!
                    binding.rvListHistory.adapter = adapter
                }

                Resource.Status.ERROR -> Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()

            }

        }
        coinHistoryViewModel.allAlerts.observe(viewLifecycleOwner) { list ->
            alertsAdapter = CoinAlertsAdapter(list, longClickListener =
            {
                coinHistoryViewModel.deleteAlert(it)
            })
            binding.rvListAlert.adapter = alertsAdapter

        }
    }
}