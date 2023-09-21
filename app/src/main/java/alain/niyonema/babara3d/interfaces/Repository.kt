package alain.niyonema.babara3d.interfaces

import alain.niyonema.babara3d.data.User

interface Repository {

    suspend fun getUser(): User

}