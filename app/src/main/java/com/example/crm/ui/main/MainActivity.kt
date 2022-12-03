package com.example.crm.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crm.databinding.ActivityMainBinding
import com.example.crm.model.Customer
import com.example.crm.ui.CustomersAdapter
import com.example.crm.ui.addCustomer.AddCustomerActivity
import com.example.crm.ui.updteCustomer.CustomerDetailsActivity
import com.example.crm.utils.hide
import com.example.crm.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var customersAdapter = CustomersAdapter()
    val customerViewModel : CustomerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customerViewModel.customerLiveData.observe(this, Observer {
            renderViewState(it)
        })

        setupView()
    }

    fun setupView() {
        binding.customersRv.apply {
            adapter = customersAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            customersAdapter.onItemClicked = {
                startActivity(CustomerDetailsActivity.buildIntent(this@MainActivity, it))
            }
        }

        binding.addNewCustomer.setOnClickListener {
            startActivity(AddCustomerActivity.buildIntent(this@MainActivity))
        }
    }

    private fun renderViewState(state: CustomerViewState){
        if(state.loading)
            binding.loaderView.show()
        else
            binding.loaderView.hide()

        if(!state.customersList.isNullOrEmpty()){
            binding.noDataView.hide()
            customersAdapter.submitList(state.customersList)
        }else{
            binding.noDataView.show()
        }

    }

    override fun onResume() {
        super.onResume()
        customerViewModel.getCustomersList()
    }


}