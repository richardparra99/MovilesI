package com.example.projetcmovil.data.network

import android.content.Context
import com.example.projetcmovil.util.GestorToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object InstanciaRetrofit {
    private var retrofit: Retrofit? = null

    fun obtenerInstancia(context: Context): ApiServicio {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val cliente = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging) // 👈 añade esto
            .addInterceptor { cadena ->
                val solicitud = cadena.request().newBuilder()
                val token = GestorToken(context).obtenerToken()
                if (!token.isNullOrEmpty()) {
                    solicitud.addHeader("Authorization", "Bearer $token")
                }
                cadena.proceed(solicitud.build())
            }
            .build()

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("http://trabajos.jmacboy.com/api/")
                .client(cliente)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit!!.create(ApiServicio::class.java)
    }
}