package com.example.crm.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crm.data.repositories.CustomerRepository
import com.example.crm.model.Customer
import com.example.crm.schedulers.BaseSchedulerProvider
import com.example.crm.ui.main.bases.BaseViewModel
import com.example.crm.usecases.CustomerUseCase

class CustomerViewModel(
    schedulerProvider: BaseSchedulerProvider,
    private val repository: CustomerRepository
) : BaseViewModel(schedulerProvider) {

    private var _CustomerLiveData = MutableLiveData<CustomerViewState>()
    var customerLiveData = _CustomerLiveData

    init {
        _CustomerLiveData.value = CustomerViewState()
    }

    // observe on database instance to get customer list
    fun getCustomersList(){
        add{
            repository.getCustomersList()
                .compose(applyObservableSchedulers())
                .doOnSubscribe {
                    _CustomerLiveData.value = _CustomerLiveData.value?.copy(loading = true)
                }
                .subscribe ({
                    _CustomerLiveData.value = _CustomerLiveData.value?.copy(loading = false, customersList = it)

                }, {
                    _CustomerLiveData.value = _CustomerLiveData.value?.copy(loading = false, error = it.message.toString())

                })

        }
    }
// add customer to data base
    fun addNewCustomer(customer: Customer){
        add{
            repository.addNewCustomer(customer)
                .compose(applyCompletableSchedulers())
                .doOnSubscribe {
                    _CustomerLiveData.value = _CustomerLiveData.value?.copy(loading = true)
                }
                .subscribe({
                    _CustomerLiveData.value = _CustomerLiveData.value?.copy(loading = false, customerAdded = true)

                },{
                    it
                })
        }
    }

    fun updateCustomer(customer: Customer){
        add{
            repository.updateCustomer(customer)
                .compose(applyCompletableSchedulers())
                .doOnSubscribe {
                    _CustomerLiveData.value = _CustomerLiveData.value?.copy(loading = true)
                }
                .subscribe({
                    _CustomerLiveData.value = _CustomerLiveData.value?.copy(loading = false, customerUpdated = true)

                },{
                    it
                })
        }
    }


}