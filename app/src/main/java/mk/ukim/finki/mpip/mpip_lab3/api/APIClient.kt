package mk.ukim.finki.mpip.mpip_lab3.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {

    companion object {
        private var omdbApi: APIOmdb? = null

        fun getOmdbApi(): APIOmdb? {

            if (omdbApi == null) {
                omdbApi = Retrofit.Builder()
                    .baseUrl("https://www.omdbapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(APIOmdb::class.java)
            }
            return omdbApi
        }

    }
}