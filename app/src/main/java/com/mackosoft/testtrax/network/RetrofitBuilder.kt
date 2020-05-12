package com.mackosoft.testtrax.network

import android.content.Context
import com.mackosoft.testtrax.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
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

internal object RetrofitBuilder {

    private const val URL = "https://testepg.r0ro.fr/"

    private fun buildRetrofitService(context: Context): Retrofit {
        val client = with (OkHttpClient.Builder()) {
            setupSSLConfig(context = context, builder = this)
            this.build()
        }

        return Retrofit.Builder()
            .baseUrl(URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    // Inspired from https://adiyatmubarak.wordpress.com/2016/01/19/adding-ssl-certificate-to-retrofit-2/
    @Throws(
        CertificateException::class,
        IOException::class,
        KeyStoreException::class,
        NoSuchAlgorithmException::class,
        KeyManagementException::class,
        IllegalStateException::class)
    private fun setupSSLConfig(context: Context, builder: OkHttpClient.Builder) {

        // Loading CAs from an InputStream
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val certificate = with (context.resources.openRawResource(R.raw.certificate)) {
            certificateFactory.generateCertificate(this)
        }

        // Creating a KeyStore containing our trusted CAs
        val keystore = KeyStore.getInstance(KeyStore.getDefaultType())
        keystore.load(null, null)
        keystore.setCertificateEntry("ca", certificate)

        // Creating a TrustManager that trusts the CAs in our KeyStore.
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keystore)

        val managers = trustManagerFactory.trustManagers
        if (managers.size != 1 || managers[0] !is X509TrustManager) {
            throw java.lang.IllegalStateException("Unexpected trust managers: ${Arrays.toString(managers)}")
        }
        val x509TrustManager = managers[0] as X509TrustManager

        // Creating an SSLSocketFactory that uses our TrustManager
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf(x509TrustManager), null)

        builder.sslSocketFactory(
            sslContext.socketFactory,
            x509TrustManager
        )
    }


    fun getApiService(context: Context): ApiService = buildRetrofitService(context).create(ApiService::class.java)
}