package ir.sanaitgroup.tourism.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MainServiceLevel {
    @SerializedName("response")
    @Expose
    var response: Response? = null

    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    class Response{
        @SerializedName("schedule_available")
        @Expose
        var ServicesLevel: List<ServicesLevel>? = null

        @SerializedName("salons")
        @Expose
        var salons: List<salons>? = null
    }

    class salons {
        @SerializedName("salons_id")
        @Expose
        var salons_id: Int = 0

        @SerializedName("salon")
        @Expose
        var salon: String = ""
    }

    class ServicesLevel {

        @SerializedName("id")
        @Expose
        var id: Int = 0

        @SerializedName("start_time")
        @Expose
        var start_time: String = ""

        @SerializedName("end_time")
        @Expose
        var end_time: String = ""

        @SerializedName("price")
        @Expose
        var price: String = ""

        @SerializedName("capacity")
        @Expose
        var capacity: Int = 0

        @SerializedName("gender")
        @Expose
        var gender: Int = 0

        @SerializedName("class")
        @Expose
        var class_model: String = ""

        @SerializedName("rate")
        @Expose
        var rate: Int = 0

        @SerializedName("discount_percent")
        @Expose
        var discount_percent: Int = 0

        @SerializedName("salons_id")
        @Expose
        var salons_id: Int = 0

        @SerializedName("salon")
        @Expose
        var salon: String = ""

        @SerializedName("date")
        @Expose
        var date: String = ""

        @SerializedName("scheme")
        @Expose
        val scheme: Scheme? = null

        @SerializedName("free_seats")
        @Expose
        val free_seats: List<Free_Seats>? = null

        @SerializedName("reserve_seats")
        @Expose
        val reserve_seats: List<Reserve_Seats>? = null

        @SerializedName("details_date")
        @Expose
        val details_date: DetailsDate? = null

        class Scheme{
            @SerializedName("row")
            @Expose
            var row: Int = 0

            @SerializedName("col")
            @Expose
            var col: Int = 0
        }

        class Free_Seats{
            @SerializedName("position_id")
            @Expose
            var position_id: String = ""
        }

        class Reserve_Seats{
            @SerializedName("position_id")
            @Expose
            var position_id: String = ""
        }

        class DetailsDate {
            @SerializedName("mday")
            @Expose
            var day: Int = 0

            @SerializedName("mon")
            @Expose
            var mon: Int = 0

            @SerializedName("year")
            @Expose
            var year: Int = 0

            @SerializedName("weekday")
            @Expose
            var weekday: String = ""

            @SerializedName("month")
            @Expose
            var month: String = ""
        }

        class ServicesLevel {
            var id: Int = 0
            var salons_id: Int = 0
            var salon: String = ""
            var class_model: String = ""
            var date: String = ""
        }
    }
}