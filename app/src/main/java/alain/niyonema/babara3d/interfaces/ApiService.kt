package alain.niyonema.babara3d.interfaces

import alain.niyonema.babara3d.network.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("v2/users")
    suspend fun getUser(): Response<UserResponse>

}
