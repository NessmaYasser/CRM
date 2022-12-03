package com.example.crm.ui.main.bases

import androidx.lifecycle.ViewModel
import com.example.crm.schedulers.BaseSchedulerProvider
import io.reactivex.CompletableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel(var schedulerProvider: BaseSchedulerProvider) : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    fun add(disposable : () -> Disposable){
        compositeDisposable.add(disposable())
    }

    fun <x> applyObservableSchedulers() : ObservableTransformer<x,x>{
        return ObservableTransformer{ it ->
            it.subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
        }
    }

    fun applyCompletableSchedulers() : CompletableTransformer{
        return CompletableTransformer{ it ->
            it.subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}