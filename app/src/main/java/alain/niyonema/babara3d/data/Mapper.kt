package alain.niyonema.babara3d.data

import alain.niyonema.babara3d.network.UserResponse
import java.text.SimpleDateFormat
import java.util.Locale

fun UserResponse.asUserDTO(): User {

    val beFormat = "yyyy-MM-dd"
    val birthDate = SimpleDateFormat(
        beFormat,
        Locale.getDefault()
    ).parse(dateOfBirth)
    return User(
        username = username,
        avatarUrl = avatar,
        email = email,
        phoneNumber = phoneNumber,
        birthDate = birthDate,
        country = address.country,
        city = address.city,
        streetName = address.streetName,
        streetAddress = address.streetAddress,
        zipCode = address.zipCode,
        plan = subscription.plan,
        status = subscription.status,
        paymentMethod = subscription.paymentMethod,
        term = subscription.term,
    )

}