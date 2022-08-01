package com.demo.cointrackerapp.ui.list

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.cointrackerapp.R
import com.demo.cointrackerapp.adapter.CoinListAdapter
import com.demo.cointrackerapp.data.local.Alerts
import com.demo.cointrackerapp.databinding.FragmentCoinlistBinding
import com.demo.cointrackerapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import timber.log.Timber


@AndroidEntryPoint
@WithFragmentBindings
class CoinListFragment : Fragment(R.layout.fragment_coinlist) {

    private val coinListViewModel: CoinListViewModel by viewModels()
    private lateinit var adapter: CoinListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCoinlistBinding.bind(view)
        binding.rvList.layoutManager = LinearLayoutManager(context)
        binding.rvList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        coinListViewModel.fetchCoinPrices()

        coinListViewModel.isInserting.observe(viewLifecycleOwner) {
            if (!it) {
                Toast.makeText(context, "Alert has been added.", Toast.LENGTH_SHORT).show()
            }
        }

        coinListViewModel.response.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.LOADING -> {
                    Timber.d("Result is Loading.")
                }

                Resource.Status.SUCCESS -> {
                    adapter = resource.data?.let { it1 ->
                        CoinListAdapter(it1) {
                            when (it) {
                                "btc" -> createDialog(it)
                                "eth" -> createDialog(it)
                                "xrp" -> createDialog(it)
                                else -> findNavController().navigate(
                                    CoinListFragmentDirections.actionNavigationListToNavigationHistory(
                                        it
                                    )
                                )
                            }
                        }
                    }!!
                    binding.rvList.adapter = adapter
                }

                Resource.Status.ERROR -> Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()

            }

        }

    }

    private fun createDialog(source: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val view = LayoutInflater.from(context).inflate(R.layout.alert_layout, null)

        builder.setTitle("Set limits")
            .setMessage("Notifications will be sent to you when the value goes above or below the maximum and minimum values")
            .setView(view)

        val minEditText: EditText = view.findViewById(R.id.minimumValue)
        minEditText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        val maxEditText: EditText = view.findViewById(R.id.maximumValue)
        maxEditText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        val dialog = builder.create()

        view.findViewById<Button>(R.id.save).setOnClickListener {
            if (!minEditText.text.isNullOrEmpty() && !maxEditText.text.isNullOrEmpty()) {
                coinListViewModel.insertAlert(
                    Alerts(
                        source,
                        minEditText.text.toString().toFloat(),
                        maxEditText.text.toString().toFloat()
                    )
                )
                dialog.dismiss()
            }

        }
        dialog.show()
    }

}