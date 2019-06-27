package ir.sanaitgroup.tourism.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import ir.sanaitgroup.tourism.*
import ir.sanaitgroup.tourism.utils.MySetting
import ir.sanaitgroup.tourism.activities.ServiceDetailActivity
import ir.sanaitgroup.tourism.models.MainServices
import kotlinx.android.synthetic.main.card_service.view.*
import java.lang.Exception

class ServiceAdapter(val service : List<MainServices.Info>, val activity: Activity) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return service.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.card_service, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_description.setText(service[position].explanation,true)
        holder.txt_title.text = service[position].service_name
        if(position == 0)
            holder.li_line.visibility = View.INVISIBLE
        else
            holder.li_line.visibility = View.VISIBLE
        if (service[position].picture_small.contains("./")){
            val picAddress = MySetting().getSetting(activity).url + service[position].picture_small
            holder.mk_loading.visibility = View.VISIBLE
            Picasso.get().load(picAddress).placeholder(R.drawable.default_back).into(holder.img_picture , object :Callback{
                override fun onError(e: Exception?) {
                    holder.mk_loading.visibility = View.GONE
                    holder.img_picture.setImageResource(R.drawable.default_back)
                }
                override fun onSuccess() {
                    holder.mk_loading.visibility = View.GONE
                }
            })
        }

        holder.cardView.setOnClickListener {
            val intent = Intent(activity,ServiceDetailActivity::class.java)
            intent.putExtra("id",service[position].id)
            intent.putExtra("title",service[position].service_name)
            intent.putExtra("description",service[position].explanation)
            intent.putExtra("picture",service[position].picture_big)
            intent.putExtra("all_pictures",service[position].pictures_all)
            activity.startActivity(intent)
        }
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val txt_description = view.txt_description
    val txt_title = view.txt_title
    val img_picture = view.img_picture
    val mk_loading = view.mk_loading
    val li_line = view.li_line
    val cardView = view
}