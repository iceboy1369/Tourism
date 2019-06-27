package ir.sanaitgroup.tourism.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MainServices {
    @SerializedName("response")
    @Expose
    var info: List<Info>? = null

    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    class Info{
        @SerializedName("id")
        @Expose
        var id: Int = 0

        @SerializedName("tourism_service")
        @Expose
        var service_name: String = ""

        @SerializedName("explanation")
        @Expose
        var explanation: String = ""

        @SerializedName("images_path_400_560")
        @Expose
        var picture_small: String = ""

        @SerializedName("images_path_1200_350")
        @Expose
        var picture_big: String = ""

        @SerializedName("images_path_600_400")
        @Expose
        var pictures_all: String = ""

    }
}