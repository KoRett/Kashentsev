package com.korett.kashentsev.presentation.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.korett.kashentsev.domain.model.ResultModel

typealias LiveResult<T> = LiveData<ResultModel<T>>
typealias MutableLiveResult<T> = MutableLiveData<ResultModel<T>>
typealias MediatorLiveResult<T> = MediatorLiveData<ResultModel<T>>