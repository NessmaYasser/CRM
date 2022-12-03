package com.example.crm.di

import com.example.crm.data.local.CRMDBFactory
import com.example.crm.data.repositories.CustomerRepository
import com.example.crm.schedulers.BaseSchedulerProvider
import com.example.crm.schedulers.SchedulerProvider
import com.example.crm.ui.main.CustomerViewModel
import com.example.crm.usecases.CustomerUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module {
    //database instance
    single {
        CRMDBFactory.getInstance(androidContext()).crmDao()
    }
    factory<BaseSchedulerProvider> { SchedulerProvider() }
    //repos
    factory { CustomerRepository(get()) }
    //useCases
    factory { CustomerUseCase(get()) }
    //viewModels
    viewModel { CustomerViewModel(get(), get()) }

}