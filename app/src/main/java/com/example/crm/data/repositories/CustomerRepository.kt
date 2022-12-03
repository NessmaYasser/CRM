package com.example.crm.data.repositories

import com.example.crm.data.local.CRMDao
import com.example.crm.model.Customer

class CustomerRepository(private val crmDB : CRMDao) {

    fun getCustomersList() = crmDB.getAllCustomers()

    fun addNewCustomer(customer: Customer) = crmDB.addNewCustomer(customer)

    fun updateCustomer(customer: Customer) = crmDB.updateCustomer(customer)
}