package com.mimo.android.data.network

import com.mimo.android.data.util.NetworkContract
import com.mimo.android.domain.model.ApiResponse
import com.mimo.android.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        var accessToken: String? = runBlocking {
            var token: String? = null
            dataStoreRepository.getUserToken().collectLatest { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        token = response.data.accessToken
                    }

                    else -> {
                        token = null
                    }
                }
            }
            token
        }
        val request =
            requestBuilder.header(NetworkContract.Authorization, accessToken ?: "")
                .build()
        return chain.proceed(request)
    }
}
