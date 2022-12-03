package com.example.crm.data.local

import androidx.room.*
import com.example.crm.model.Customer
import io.reactivex.Completable
import io.reactivex.Observable
/*
 * Created by Nesma Yasser.
 *
 * Dao interface contains functions for accessing
 * the contents in the table.
 */
@Dao
interface CRMDao {
    //get all customers from database
    @Query("SELECT * from Customer")
    fun getAllCustomers() : Observable<List<Customer>>

    //add new customer in database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewCustomer(customer: Customer): Completable

    //update data of a customer
    @Update
    fun updateCustomer(customer: Customer): Completable
}