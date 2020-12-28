package com.lucascabral.taskapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lucascabral.taskapplication.service.constants.TaskConstants
import com.lucascabral.taskapplication.service.repository.local.SecurityPreferences

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mSharedPreferences = SecurityPreferences(application)

    private val mUserName = MutableLiveData<String>()
    var userName: LiveData<String> = mUserName

    fun loadUserName() {
        mUserName.value = mSharedPreferences.get(TaskConstants.SHARED.PERSON_NAME)
    }
}