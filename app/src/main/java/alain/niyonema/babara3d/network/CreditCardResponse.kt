package alain.niyonema.babara3d.network

import com.google.gson.annotations.SerializedName

data class CreditCardResponse(

    @SerializedName("cc_number") val number: String,

)
