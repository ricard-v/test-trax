package com.mackosoft.testtrax.network

import android.content.Context
import android.util.Log
import com.mackosoft.testtrax.network.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkApi(context: Context) {

    private companion object {
        const val TAG = "ApiClient"
    }


    private val apiService = RetrofitBuilder.getApiService(context)


    suspend fun getMovies(): Result<List<Movie>> {
        return try {
            withContext(Dispatchers.IO) {
                Result.Success( apiService.getMovies() ).also {
                    Log.i(TAG, "Successfully loaded movies")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get movies -> ${e.message}")
            Log.getStackTraceString(e)
            Result.Error("Failed to get movies -> ${e.message}")
        }
    }
}