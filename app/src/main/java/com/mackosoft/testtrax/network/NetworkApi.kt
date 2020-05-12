package com.mackosoft.testtrax.network

import android.content.Context
import android.util.Log
import com.google.gson.stream.JsonReader
import com.mackosoft.testtrax.R
import com.mackosoft.testtrax.model.Movie
import com.mackosoft.testtrax.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

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