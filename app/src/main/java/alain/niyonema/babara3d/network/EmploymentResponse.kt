package alain.niyonema.babara3d.network

import com.google.gson.annotations.SerializedName

data class EmploymentResponse(

    @SerializedName("title") val title: String,
    @SerializedName("key_skill") val keySkill: String

)
