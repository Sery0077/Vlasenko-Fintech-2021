package sery.vlasenko.developerslife.data.repository

import sery.vlasenko.developerslife.data.RandomGifDAO
import sery.vlasenko.developerslife.data.ResponseData
import java.io.IOException
import javax.inject.Inject

class RandomGifRepository @Inject constructor(private  var service: DevLifeService) {

    suspend fun getRandomGif(): ResponseData<RandomGifDAO> {
        return try {
            val response = service.getRandomGif()

            if (response.isSuccessful) {
                ResponseData(response.body(), null)
            } else {
                ResponseData(null, response.code())
            }
        } catch (e: IOException) {
            ResponseData(null, e.localizedMessage)
        }
    }
}