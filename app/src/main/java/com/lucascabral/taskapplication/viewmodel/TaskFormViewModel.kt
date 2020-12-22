package com.lucascabral.taskapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lucascabral.taskapplication.service.model.PriorityModel
import com.lucascabral.taskapplication.service.repository.PriorityRepository

class TaskFormViewModel(application: Application) : AndroidViewModel(application) {

    private val mPriorityRepository = PriorityRepository(application)

    private val mPriorityList = MutableLiveData<List<PriorityModel>>()
    var priorities: LiveData<List<PriorityModel>> = mPriorityList

    fun listPriorities() {
        mPriorityList.value = mPriorityRepository.list()
    }

}