package alain.niyonema.babara3d.repositories

import alain.niyonema.babara3d.data.User
import alain.niyonema.babara3d.interfaces.Repository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(): User {
        return repository.getUser()
    }

}