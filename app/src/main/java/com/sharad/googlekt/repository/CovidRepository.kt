package com.sharad.googlekt.repository

import com.sharad.googlekt.utils.ApiService
import com.sharad.googlekt.model.StateWise
import com.sharad.googlekt.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

@FlowPreview
@ExperimentalCoroutinesApi
class CovidRepository(private val apiService: ApiService) {

    fun getData(): Flow<State<StateWise>> {
        return object : NetworkBoundRepository<StateWise>() {
            override suspend fun fetchFromRemote(): Response<StateWise> = apiService.getData()
        }.asFlow().flowOn(Dispatchers.IO)
    }

}