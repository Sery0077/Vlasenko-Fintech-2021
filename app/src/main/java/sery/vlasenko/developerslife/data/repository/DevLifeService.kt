package sery.vlasenko.developerslife.data.repository

import retrofit2.Response
import retrofit2.http.GET
import sery.vlasenko.developerslife.data.RandomGif

interface DevLifeService {
    @GET("random")
    suspend fun getRandomGif(): Response<RandomGif>
}