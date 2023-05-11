package com.puzzle_agency.newsapp.features.news_shared.data.util

import android.util.Log
import com.google.android.gms.common.api.ApiException
import com.puzzle_agency.newsapp.features.news_shared.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response

inline fun <RequestType> safeApiCall(
    crossinline apiCall: suspend () -> Response<RequestType>
) = channelFlow<Resource<RequestType>> {
    withContext(Dispatchers.IO) {
        send(Resource.Loading())

        try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                response.body()?.let {
                    send(Resource.Success(it))
                }
            } else {
                val errorBody = JSONObject(response.errorBody()?.string() ?: "")
                send(Resource.Error(errorBody.getString("message")))
            }
        } catch (e: ApiException) {
            Log.d("SafeApiCall", e.message.toString())
            send(Resource.Error(e.message.toString()))
        } catch (e: Throwable) {
            Log.d("SafeApiCall", e.message.toString())
            send(Resource.Error(e.message.toString()))
        }
    }
}