package com.example.crm.ui.addCustomer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.crm.R
import com.example.crm.databinding.ActivityAddCustomerBinding
import com.example.crm.model.Customer
import com.example.crm.model.CustomerActions
import com.example.crm.ui.main.CustomerViewModel
import com.example.crm.ui.main.CustomerViewState
import com.example.crm.utils.Helper
import com.example.crm.utils.custom.CustomSpinnerAdapter
import com.example.crm.utils.hide
import com.example.crm.utils.show
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_customer.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * created by Nesma Yasser
 *
 * activity to add new customer
 */
class AddCustomerActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddCustomerBinding
    lateinit var spinnerAdapter: CustomSpinnerAdapter
    private val customerViewModel: CustomerViewModel by viewModel()
    var actionType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCustomerBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        customerViewModel.customerLiveData.observe(this, Observer {
            renderViewState(it)
        })

        setupView()
    }

    fun setupView() {
        //set spinner of actions
        spinnerAdapter = CustomSpinnerAdapter(
            this@AddCustomerActivity,
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
                        }
                        2 -> {
                            binding.countryCodeV.hide()
                            binding.phoneV.hide()
                            binding.locationV.show()
                            binding.followUpV.hide()
                            actionType = "Visit"
                        }
                        3 -> {
                            binding.countryCodeV.hide()
                            binding.phoneV.hide()
                            binding.locationV.hide()
                            binding.followUpV.show()
                            actionType = "Follow up"
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
// fields validations and if is valid, add data to database
        binding.addBtn.setOnClickListener {
            if (Helper.validateWidget(binding.nameV).first) {
                if (actionType.equals("")) {
                    Snackbar.make(binding.root, R.string.action_selection_warning, Snackbar.LENGTH_LONG)
                        .show()
                }
                else if (actionType == "Call") {
                    if (!Helper.validateWidget(binding.phoneV).first || !Helper.validateWidget(
                            binding.countryCodeV
                        ).first
                    ) {
                        binding.phoneV.error = Helper.validateWidget(binding.phoneV).second
                        binding.countryCodeV.error =
                            Helper.validateWidget(binding.countryCodeV).second
                    } else {
                        customerViewModel.addNewCustomer(
                            Customer(
                                Name = nameEt.text.toString(),
                                Actions = listOf<CustomerActions>(CustomerActions(ActionType = actionType, ActionDetails = "(${countryCodeET.text.toString()}) ${phoneET.text.toString()}"))
                            )
                        )
                    }
                } else if(actionType == "Visit") {
                    if (!Helper.validateWidget(binding.locationV).first) {
                        binding.locationV.error = Helper.validateWidget(binding.locationV).second
                    } else {
                        customerViewModel.addNewCustomer(
                            Customer(
                                Name = binding.nameEt.text.toString(),
                                Actions = listOf<CustomerActions>(CustomerActions(ActionType = actionType, ActionDetails = binding.locationET.text.toString()))
                            )
                        )
                    }
                }else{
                    if (!Helper.validateWidget(binding.followUpV).first) {
                        binding.followUpV.error = Helper.validateWidget(binding.followUpV).second
                    } else {
                        customerViewModel.addNewCustomer(
                            Customer(
                                Name = nameEt.text.toString(),
                                Actions = listOf<CustomerActions>(CustomerActions(ActionType = actionType, ActionDetails = binding.followUpET.text.toString()))
                            )
                        )
                    }
                }
            } else {
                binding.nameV.error = Helper.validateWidget(binding.nameV).second
            }
        }
    }

    private fun renderViewState(state: CustomerViewState) {
        if (state.loading)
            binding.loaderView.show()
        else
            binding.loaderView.hide()

        if (state.customerAdded)
            onBackPressed()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    companion object {
        fun buildIntent(context: Context): Intent = Intent(context, AddCustomerActivity::class.java)
    }
}