package sery.vlasenko.developerslife.data.repository

import retrofit2.Response
import retrofit2.http.GET
import sery.vlasenko.developerslife.data.RandomGifDAO

interface DevLifeService {
    @GET("random")
    suspend fun getRandomGif(): Response<RandomGifDAO>
}