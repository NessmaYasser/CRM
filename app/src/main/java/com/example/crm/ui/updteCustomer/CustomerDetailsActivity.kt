package com.example.crm.ui.updteCustomer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crm.R
import com.example.crm.databinding.ActivityCustomerDetailsBinding
import com.example.crm.model.Customer
import com.example.crm.model.CustomerActions
import com.example.crm.ui.main.CustomerViewModel
import com.example.crm.ui.main.CustomerViewState
import com.example.crm.ui.main.adapters.ActionsAdapter
import com.example.crm.utils.Helper
import com.example.crm.utils.custom.CustomSpinnerAdapter
import com.example.crm.utils.hide
import com.example.crm.utils.show
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class CustomerDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityCustomerDetailsBinding
    lateinit var spinnerAdapter: CustomSpinnerAdapter
    private val customerViewModel: CustomerViewModel by viewModel()
    var actionType = ""
    var customer: Customer? = null
    var actionsAdapter = ActionsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = com.example.crm.databinding.ActivityCustomerDetailsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        customerViewModel.customerLiveData.observe(this, Observer {
            renderViewState(it)
        })

        setupView()
    }

    fun setupView() {

        customer = intent.getParcelableExtra(KEY_CUSTOMER)
        if (customer != null) {
            binding.nameTv.text = customer!!.Name
            if (!customer!!.Actions.isNullOrEmpty()) {
                binding.customersRv.apply {
                    adapter = actionsAdapter
                    layoutManager = LinearLayoutManager(this@CustomerDetailsActivity)
                    actionsAdapter.submitList(customer!!.Actions)
                }
            }


            binding.addActionTv.setOnClickListener {
                binding.actionSpinnerView.show()
                binding.dividerV.show()
            }

            spinnerAdapter = CustomSpinnerAdapter(
                this@CustomerDetailsActivity,
                R.layout.unit_spinner_item,
                resources.getStringArray(R.array.actions).toList()
            )
            binding.actionSpinnerView.adapter = spinnerAdapter
            binding.actionSpinnerView.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        position: Int,
                        p3: Long
                    ) {
                        when (position) {
                            0 -> {
                                binding.updateBtn.hide()
                                binding.locationV.hide()
                                binding.countryCodeV.hide()
                                binding.phoneV.hide()
                                binding.followUpV.hide()
                            }
                            1 -> {
                                binding.countryCodeV.show()
                                binding.phoneV.show()
                                binding.locationV.hide()
                                binding.followUpV.hide()
                                actionType = "Call"
                                binding.updateBtn.show()
                            }
                            2 -> {
                                binding.countryCodeV.hide()
                                binding.phoneV.hide()
                                binding.locationV.show()
                                binding.followUpV.hide()
                                actionType = "Visit"
                                binding.updateBtn.show()
                            }
                            3 -> {
                                binding.countryCodeV.hide()
                                binding.phoneV.hide()
                                binding.locationV.hide()
                                binding.followUpV.show()
                                actionType = "Follow up"
                                binding.updateBtn.show()
                            }
                        }
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                }

            binding.updateBtn.setOnClickListener {
                if (actionType.equals("")) {
                    Snackbar.make(
                        binding.root,
                        R.string.action_selection_warning,
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                } else if (actionType == "Call") {
                    if (!Helper.validateWidget(binding.phoneV).first || !Helper.validateWidget(
                            binding.countryCodeV
                        ).first
                    ) {
                        binding.phoneV.error = Helper.validateWidget(binding.phoneV).second
                        binding.countryCodeV.error =
                            Helper.validateWidget(binding.countryCodeV).second
                    } else {
                        var actionsList = mutableListOf<CustomerActions>()
                        if(!customer!!.Actions.isNullOrEmpty()){
                            actionsList.addAll(customer!!.Actions!!)
                            actionsList.add(CustomerActions(
                                ActionType = actionType,
                                ActionDetails ="(${binding.countryCodeET.text.toString()}) ${binding.phoneET.text.toString()}")
                            )
                        }else{
                            actionsList.add(CustomerActions(
                                ActionType = actionType,
                                ActionDetails = "(${binding.countryCodeET.text.toString()}) ${binding.phoneET.text.toString()}"
                            ))
                        }
                        customerViewModel.updateCustomer(
                            Customer(
                                Id = customer!!.Id,
                                Name = customer!!.Name,
                                Actions = actionsList
                            )
                        )
                    }
                } else if(actionType == "Visit"){
                    if (!Helper.validateWidget(binding.locationV).first) {
                        binding.locationV.error = Helper.validateWidget(binding.locationV).second
                    } else {
                        var actionsList = mutableListOf<CustomerActions>()
                        if(!customer!!.Actions.isNullOrEmpty()){
                            actionsList.addAll(customer!!.Actions!!)
                            actionsList.add(CustomerActions(
                                ActionType = actionType,
                                ActionDetails = binding.locationET.text.toString()
                            ))
                        }else{
                            actionsList.add(CustomerActions(
                                ActionType = actionType,
                                ActionDetails = binding.locationET.text.toString()
                            ))
                        }
                        customerViewModel.updateCustomer(
                            Customer(
                                Id = customer!!.Id,
                                Name = customer!!.Name,
                                Actions = actionsList
                            )
                        )
                    }
                }
                else {
                    if (!Helper.validateWidget(binding.followUpV).first) {
                        binding.followUpV.error = Helper.validateWidget(binding.followUpV).second
                    } else {
                        var actionsList = mutableListOf<CustomerActions>()
                        if(!customer!!.Actions.isNullOrEmpty()){
                            actionsList.addAll(customer!!.Actions!!)
                            actionsList.add(CustomerActions(
                                ActionType = actionType,
                                ActionDetails = binding.followUpET.text.toString()
                            ))
                        }else{
                            actionsList.add(CustomerActions(
                                ActionType = actionType,
                                ActionDetails = binding.followUpET.text.toString()
                            ))
                        }
                        customerViewModel.updateCustomer(
                            Customer(
                                Id = customer!!.Id,
                                Name = customer!!.Name,
                                Actions = actionsList
                            )
                        )
                    }
                }
            }
        }
    }

    fun renderViewState(state: CustomerViewState) {
        if (state.customerUpdated) {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    companion object {

        val KEY_CUSTOMER = "customer"

        fun buildIntent(context: Context, customer: Customer): Intent {
            var intent = Intent(context, CustomerDetailsActivity::class.java)
            intent.putExtra(KEY_CUSTOMER, customer)
            return intent
        }
    }

}