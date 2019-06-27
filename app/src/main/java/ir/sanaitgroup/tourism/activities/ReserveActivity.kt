package ir.sanaitgroup.tourism.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import ir.sanaitgroup.tourism.utils.DataBase
import ir.sanaitgroup.tourism.utils.G
import ir.sanaitgroup.tourism.R
import ir.sanaitgroup.tourism.adapters.ChairAdapter
import kotlinx.android.synthetic.main.activity_reserve.*

class ReserveActivity: AppCompatActivity() {

    var time_id = 0
    var salon_id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve)

        getIntentValue()
        setDefaultValues()
    }
    override fun onDestroy() {
        super.onDestroy()
        overridePendingTransitionExit()
    }
    override fun onStart() {
        super.onStart()
        overridePendingTransitionEnter()
    }

    // set animate on enter activity
    private fun overridePendingTransitionEnter() {
        overridePendingTransition(
            R.anim.slide_from_left,
            R.anim.slide_to_right
        )
    }
    // set animate on close activity
    private fun overridePendingTransitionExit() {
        overridePendingTransition(
            R.anim.slide_from_right,
            R.anim.slide_to_left
        )
    }

    private fun getIntentValue() {
        val bundle = intent.extras
        if (bundle != null) {
            time_id = bundle.getInt("id")
        }
    }
    private fun setDefaultValues(){
        val db = DataBase(G.context(), null)
        val row_size = db.getRowCol(time_id).row

//        Toast.makeText(G.context(),row_size.toString(),Toast.LENGTH_SHORT).show()
        val adapter = ChairAdapter(db.getAllFreeSeats(time_id),this)
        chairs_recyclerView.layoutManager = GridLayoutManager(G.context(),row_size)
        chairs_recyclerView.adapter = adapter
    }
}