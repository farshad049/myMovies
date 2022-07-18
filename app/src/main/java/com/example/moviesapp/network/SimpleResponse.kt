package com.example.moviesapp.network

import retrofit2.Response


//this is for checking if network request was failure or successful
data class SimpleResponse<T>(
    val status: Status,
    //nullable because response can be failed, and Response is the retrofit response
    val data: Response<T>?,
    //nullable because we could have no exception error
    val exception: Exception?
) {

    sealed class Status {
        object Success : Status()
        object Failure : Status()
    }

    val failed: Boolean
        get() = this.status == Status.Failure

    val isSuccessful: Boolean
        get() = !failed && this.data?.isSuccessful == true
    //T is a temporary value
    val body: T
        get() = this.data!!.body()!!

    val bodyNullable: T?
        get() = this.data?.body()







    companion object {
        //because T is temporary we have to pass it to function
        fun <T> success(data: Response<T>): SimpleResponse<T> {
            return SimpleResponse(
                status = Status.Success,
                data = data,
                exception = null
            )
        }

        fun <T> failure(exception: Exception): SimpleResponse<T> {
            return SimpleResponse(
                status = Status.Failure,
                data = null,
                exception = exception
            )
        }
    }


}