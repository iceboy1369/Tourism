package ir.sanaitgroup.tourism.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.custom.sliderimage.logic.SliderImage
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import ir.icegroup.utilities.Utility
import ir.sanaitgroup.tourism.*
import ir.sanaitgroup.tourism.utils.*
import ir.sanaitgroup.tourism.adapters.ServiceLevelHeaderAdapter
import ir.sanaitgroup.tourism.models.MainServiceLevel
import ir.sanaitgroup.tourism.rest.RetrofitFactory
import kotlinx.android.synthetic.main.activity_service_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceDetailActivity : AppCompatActivity() {

    private lateinit var callRequest: Call<MainServiceLevel>
    private var id = 0
    private val db = DataBase(this, null)
    private val allPictures = ArrayList<String>()
    private val slider = SliderImage(G.context())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_detail)
        setSupportActionBar(toolbar)

        getMyIntent()
        initListeners()

    }
    // set custom font to activity view
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
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

    private fun getMyIntent(){
        val bundle = intent.extras
        if(bundle!=null) {
            id = bundle.getInt("id")
            val title = bundle.getString("title")
            val description = bundle.getString("description")
            val picture = bundle.getString("picture")

            val pictures = bundle.getString("all_pictures")
            val all = pictures!!.split(",")
            val picsCount = all.size - 1

            allPictures.add(MySetting().getSetting(this).url + picture)
            for (i in 0..picsCount){
                val url = MySetting().getSetting(this).url + all[i]
                if (url.contains(".jpg")) {
                    allPictures.add(url)
                }
            }
            txt_image_count.text = picsCount.toString()
            toolbar.title = title
            txt_description.setText("_           $description",true)
            val strPic = MySetting().getSetting(this).url + picture +""
            Picasso.get().load(strPic).placeholder(R.drawable.default_back).into(background)

            if (Utility.isNetworkConnected(this))
                getServiceLevels()
            else
                Toast.makeText(G.context(),"اینترنت موجود نیست", Toast.LENGTH_LONG).show()
        }
    }
    private fun initListeners(){
        fab_rate.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))

        }
        li_gallery.setOnClickListener {
            slider.openfullScreen(allPictures,0)
        }
        li_photo_content.setOnClickListener {
            slider.openfullScreen(allPictures,0)
        }
    }
    // function to call server and get company information
    private fun getServiceLevels() {

        val rootObject= JsonObject()
        rootObject.addProperty("TourismServicesId",1)

//        li_loading.visibility = View.VISIBLE
        val api = RetrofitFactory.makeRetrofitService(Constants.baseURL)
        callRequest = api.getScheduleAvailable(rootObject)
        callRequest.enqueue(object : Callback<MainServiceLevel> {

            override fun onResponse(call: Call<MainServiceLevel>?, response: Response<MainServiceLevel>) {
                if (response.isSuccessful ) {
                    if(response.body()!!.status == 1) {
                        val scheduleServices = response.body()!!.response!!.ServicesLevel
                        val scheduleSalons = response.body()!!.response!!.salons
                        if (scheduleServices!!.isEmpty())
                            Toast.makeText(G.context(),"زمانبندی ای تعریف نشده است", Toast.LENGTH_LONG).show()
                        else{
                            db.deleteAllService()
                            db.addServiceLevel(scheduleServices)
                            serviceLevelRecyclerView?.adapter = ServiceLevelHeaderAdapter(scheduleSalons!!,this@ServiceDetailActivity)
                            serviceLevelRecyclerView?.layoutManager = LinearLayoutManager(this@ServiceDetailActivity)
                        }
                    }else
                        Toast.makeText(G.context(),response.body()!!.description, Toast.LENGTH_LONG).show()
//                        Toast.makeText(G.context(),"در سرور با مشکلی برخوردیم لطفا بعدا تلاش کنید", Toast.LENGTH_LONG).show()

                }else
                    Toast.makeText(G.context(),"در ارتباط با سرور به مشکل خوردیم لطفا اینترنت خود را چک کنید", Toast.LENGTH_LONG).show()
                callRequest.cancel()
//                li_loading.visibility = View.GONE
            }

            override fun onFailure(call: Call<MainServiceLevel>?, t: Throwable?) {
//                li_loading.visibility = View.GONE
                Toast.makeText(G.context(),"متاسفانه در ارتباط با سرور به خطا خورده ایم\n لطفا مجدد تلاش کنید", Toast.LENGTH_LONG).show()
                callRequest.cancel()
            }
        })
    }
}