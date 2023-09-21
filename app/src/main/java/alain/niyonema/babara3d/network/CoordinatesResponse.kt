package alain.niyonema.babara3d.network

import com.google.gson.annotations.SerializedName

data class CoordinatesResponse(

    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,

)
