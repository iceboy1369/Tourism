package ir.sanaitgroup.tourism.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.sanaitgroup.tourism.utils.DataBase
import ir.sanaitgroup.tourism.R
import ir.sanaitgroup.tourism.models.MainServiceLevel
import kotlinx.android.synthetic.main.card_service_level_header.view.*

class ServiceLevelHeaderAdapter(private val service : List<MainServiceLevel.salons>
                                ,private val activity: Activity): RecyclerView.Adapter<ViewHolderHeader>() {

    private val db = DataBase(activity, null)

    override fun getItemCount(): Int {
        return service.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHeader {
        return ViewHolderHeader(LayoutInflater.from(activity).inflate(R.layout.card_service_level_header, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderHeader, position: Int) {
        holder.setIsRecyclable(false)
        holder.txt_title.text = service[position].salon
        holder.serviceLevelRecyclerView?.layoutManager = LinearLayoutManager(activity)
        holder.serviceLevelRecyclerView?.adapter = ServiceLevelContentAdapter(
            db.getServicesContent(service[position].salons_id),activity)
        holder.expandableLayout.collapse()

        holder.cardView.setOnClickListener {
            if (holder.expandableLayout.isExpanded) {
                holder.expandableLayout.collapse()
                holder.img_arrow.animate().rotation(0f).setDuration(300).start()
            } else {
                holder.expandableLayout.expand()
                holder.img_arrow.animate().rotation(-90f).setDuration(300).start()
            }
        }
    }
}

class ViewHolderHeader (view: View) : RecyclerView.ViewHolder(view) {
    val txt_title = view.txt_title
    val img_arrow = view.img_arrow
    val cardView = view
    val serviceLevelRecyclerView = view.serviceLevelRecyclerView
    val expandableLayout = view.expandable_layout
}