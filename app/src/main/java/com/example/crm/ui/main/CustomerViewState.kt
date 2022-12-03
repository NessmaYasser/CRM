package com.example.crm.ui.main

import com.example.crm.model.Customer

data class CustomerViewState(
    val loading: Boolean = false,
    val customersList: List<Customer>? = null,
    val customerAdded: Boolean = false,
    val customerUpdated: Boolean = false,
    var error : String? = null
)
