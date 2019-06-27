package ir.sanaitgroup.tourism.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.icegroup.utilities.Utility
import ir.sanaitgroup.tourism.R
import ir.sanaitgroup.tourism.activities.ReserveActivity
import ir.sanaitgroup.tourism.models.MainServiceLevel
import kotlinx.android.synthetic.main.card_service_level_content.view.*

class ServiceLevelContentAdapter(private val service : List<MainServiceLevel.ServicesLevel>
                                ,private val activity: Activity): RecyclerView.Adapter<ViewHolderContent>() {

    override fun getItemCount(): Int {
        return service.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderContent {
        return ViewHolderContent(LayoutInflater.from(activity).inflate(R.layout.card_service_level_content, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderContent, position: Int) {
        holder.weekend.text = Utility.toPersianNumber(service[position].date)
        holder.txt_capacity.text = Utility.toPersianNumber("${service[position].capacity} نفر")
        holder.rb_star.rating = service[position].rate.toFloat()
        holder.txt_class.text = service[position].class_model
        holder.txt_base_price.text = Utility.toPersianNumber(Utility.ShowMoney(service[position].price) + " تومان")
        val off_price = ((service[position].price).toInt().div(100)).times(service[position].discount_percent)
        val final_price = service[position].price.toInt()- off_price
        holder.txt_price_with_off.text = Utility.toPersianNumber(Utility.ShowMoney(final_price.toString()) + " تومان")
        holder.txt_start_time.text = Utility.toPersianNumber(service[position].start_time)
        holder.txt_end_time.text = Utility.toPersianNumber(service[position].end_time)
        holder.btn_buy.setOnClickListener{
            val intent = Intent(activity, ReserveActivity::class.java)
            intent.putExtra("id",service[position].id)
            activity.startActivity(intent)
        }
    }
}

class ViewHolderContent(view: View) : RecyclerView.ViewHolder(view) {
    val weekend = view.txt_date
    val txt_class = view.txt_class
    val rb_star = view.rb_star
    val txt_capacity = view.txt_capacity
    val txt_base_price = view.txt_base_price
    val txt_price_with_off = view.txt_price_with_off
    val txt_start_time = view.txt_start_time
    val txt_end_time = view.txt_end_time
    val btn_buy = view.btn_buy
}