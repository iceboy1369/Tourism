package ir.sanaitgroup.tourism.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ir.sanaitgroup.tourism.*
import ir.sanaitgroup.tourism.models.MainServiceLevel
import ir.sanaitgroup.tourism.utils.G
import kotlinx.android.synthetic.main.card_chair.view.*

class ChairAdapter(val service : List<MainServiceLevel.ServicesLevel.Free_Seats>, val activity: Activity)
    : RecyclerView.Adapter<ViewHolderChair>() {

    override fun getItemCount(): Int {
        return service.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderChair {
        return ViewHolderChair(LayoutInflater.from(activity).inflate(R.layout.card_chair, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderChair, position: Int) {

        holder.li_state.setOnClickListener {
            Toast.makeText(G.context(),service[position].position_id,Toast.LENGTH_SHORT).show()
        }
    }
}

class ViewHolderChair (view: View) : RecyclerView.ViewHolder(view) {
    val li_state = view.li_state
}