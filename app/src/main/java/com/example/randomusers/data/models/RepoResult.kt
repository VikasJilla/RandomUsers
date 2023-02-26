package com.example.randomusers.data.models

abstract class RepoResult(val data: Any?)

data class ResultSuccess<T>(var resultData: T?):RepoResult(resultData)
data class ResultFailure(var resultData: ResultError):RepoResult(resultData)