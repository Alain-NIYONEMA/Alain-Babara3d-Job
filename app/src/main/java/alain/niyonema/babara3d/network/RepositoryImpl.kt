package alain.niyonema.babara3d.network

import alain.niyonema.babara3d.data.asUserDTO
import alain.niyonema.babara3d.interfaces.Repository
import alain.niyonema.babara3d.data.User
import alain.niyonema.babara3d.interfaces.ApiService
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val service: ApiService) : Repository {

    override suspend fun getUser(): User {
        return service.getUser().body()!!.asUserDTO()
    }

}