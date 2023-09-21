package alain.niyonema.babara3d.models

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import alain.niyonema.babara3d.R
import alain.niyonema.babara3d.repositories.GetUserUseCase
import alain.niyonema.babara3d.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor(
    private val application: Application,
    private val getUserUseCase: GetUserUseCase
) :
    ViewModel() {

    fun getUser(): LiveData<User> = _user

    private val _user = MutableLiveData<User>()

    init {
        viewModelScope.launch {
            try {
                val user = getUserUseCase.execute()
                _user.postValue(user)
            } catch (e: Exception) {
                Toast.makeText(
                    application.applicationContext,
                    application.getString(R.string.error_show_user),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}