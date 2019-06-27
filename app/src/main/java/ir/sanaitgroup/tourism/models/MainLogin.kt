package ir.sanaitgroup.tourism.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MainLogin {
    @SerializedName("response")
    @Expose
    var users: List<Users>? = null

    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    class Users {
        @SerializedName("id")
        @Expose
        var id: Int = 0

        @SerializedName("name")
        @Expose
        var name: String = ""

        @SerializedName("hashtags")
        @Expose
        var hashtags: String = ""

        @SerializedName("url")
        @Expose
        var url: String = ""

        @SerializedName("gmap")
        @Expose
        var gmap: String = ""
    }
}