package ir.sanaitgroup.tourism.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MainCompanyInfo {

    @SerializedName("response")
    @Expose
    var info: Info? = null

    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    class Info {
        @SerializedName("id")
        @Expose
        var id: Int? = 0

        @SerializedName("name")
        @Expose
        var name: String? = ""

        @SerializedName("hashtags")
        @Expose
        var hashtags: String? = ""

        @SerializedName("url")
        @Expose
        var url: String? = ""

        @SerializedName("gmap")
        @Expose
        var gmap: String? = ""

        constructor(id: Int?, name: String?, hashtags: String?, url: String?, gmap: String?) {
            this.id = id
            this.name = name
            this.hashtags = hashtags
            this.url = url
            this.gmap = gmap
        }
    }
}