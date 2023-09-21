package alain.niyonema.babara3d.data

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class User(

    val username: String,
    val avatarUrl: String,
    val email: String,
    val phoneNumber: String,
    private val birthDate: Date?,
    val country: String,
    val city: String,
    val streetName: String,
    val streetAddress: String,
    val zipCode: String,
    val plan: String,
    val status: String,
    val paymentMethod: String,
    val term: String,

) {

    val birthDateAsString: String
        get() {
            return if (birthDate != null) {
                SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(
                    birthDate
                )
            } else ""
        }
}
