package com.korett.kashentsev.domain.model

sealed class ResultModel<T>

class NothingResultModel<T> : ResultModel<T>()

class PendingResultModel<T> : ResultModel<T>()

class SuccessResultModel<T>(val data: T) : ResultModel<T>()

class ErrorResultModel<T>(val exception: Exception) : ResultModel<T>()