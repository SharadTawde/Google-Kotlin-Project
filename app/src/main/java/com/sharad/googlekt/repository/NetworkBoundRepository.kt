package com.sharad.googlekt.repository

import androidx.annotation.MainThread
import com.sharad.googlekt.utils.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import retrofit2.Response

@ExperimentalCoroutinesApi
abstract class NetworkBoundRepository<T> {

    fun asFlow() = flow<State<T>> {

        emit(State.loading())

        try {
            val apiResponse = fetchFromRemote()
            val remotePosts = apiResponse.body()

            if (apiResponse.isSuccessful && remotePosts != null) {
                emit(State.success(remotePosts))
            } else {
                emit(State.error(apiResponse.message()))
            }
        } catch (e: Exception) {
            // Exception occurred! Emit error
            emit(State.error("Network error! Can't get latest data."))
            e.printStackTrace()
        }
    }

    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<T>
}