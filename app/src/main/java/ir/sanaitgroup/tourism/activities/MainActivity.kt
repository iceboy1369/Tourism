package ir.sanaitgroup.tourism.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ouattararomuald.slider.SliderAdapter
import com.ouattararomuald.slider.loaders.glide.GlideImageLoaderFactory
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import ir.icegroup.utilities.Utility
import ir.sanaitgroup.tourism.*
import ir.sanaitgroup.tourism.utils.Constants
import ir.sanaitgroup.tourism.utils.G
import ir.sanaitgroup.tourism.utils.MySetting
import ir.sanaitgroup.tourism.rest.RetrofitFactory
import ir.sanaitgroup.tourism.adapters.ServiceAdapter
import ir.sanaitgroup.tourism.models.MainCompanyInfo
import ir.sanaitgroup.tourism.models.MainServices
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.no_internet.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var callRequest: Call<MainServices>
    private lateinit var callRequest2: Call<MainCompanyInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        li_no_internet.visibility = View.GONE
        if (Utility.isNetworkConnected(this))
            getCompanyInfo()
        else
            li_no_internet.visibility = View.VISIBLE

        li_no_internet.setOnClickListener { getCompanyInfo() }
    }
    override fun onDestroy() {
        super.onDestroy()
        overridePendingTransitionExit()
    }
    override fun onStart() {
        super.onStart()
        overridePendingTransitionEnter()
    }
    // set custom font to activity view
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }


    // set animate on enter activity
    private fun overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }
    // set animate on close activity
    private fun overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }
    private fun initSlider(service: List<MainServices.Info>){


        val pages = service.size-1
        val urls: ArrayList<String> = ArrayList()
        val picAddress = MySetting().getSetting(this).url
        for (i in 0..pages) {
            urls.add("$picAddress${service[i].picture_big}")
        }
        image_slider.adapter = SliderAdapter(this, GlideImageLoaderFactory(), imageUrls = urls)
    }


    // function to call server and get all service of company
    private fun getServices() {

        li_loading.visibility = View.VISIBLE
        val api=
            RetrofitFactory.makeRetrofitService(Constants.baseURL)
        callRequest = api.getServices("{}")
        callRequest.enqueue(object : Callback<MainServices> {

            override fun onResponse(call: Call<MainServices>?, response: Response<MainServices>) {
                if (response.isSuccessful ) {
                    if(response.body()!!.status == 1){
                        val services = response.body()!!.info
                        if (services!!.isEmpty()){
                            Toast.makeText(G.context(),"is zero", Toast.LENGTH_SHORT).show()
                        }else {
                            val adapter = ServiceAdapter(services, this@MainActivity)
                            serviceRecyclerView.layoutManager =
                                LinearLayoutManager(this@MainActivity)
                            serviceRecyclerView.adapter = adapter
                            val serviceInfo: MainCompanyInfo.Info = MySetting().getSetting(this@MainActivity)
                            txt_day_choose_action_bar.text = serviceInfo.name
                            initSlider(services)
                        }
                    }else
                        Toast.makeText(G.context(),"در سرور با مشکلی برخوردیم لطفا بعدا تلاش کنید", Toast.LENGTH_LONG).show()
                }else {
                    Toast.makeText(G.context(), "در ارتباط با سرور به مشکل خوردیم لطفا اینترنت خود را چک کنید", Toast.LENGTH_LONG).show()
                    li_no_internet.visibility = View.VISIBLE
                }
                callRequest.cancel()
                li_loading.visibility = View.GONE
            }

            override fun onFailure(call: Call<MainServices>?, t: Throwable?) {
                li_loading.visibility = View.GONE
                li_no_internet.visibility = View.VISIBLE
//                Toast.makeText(G.context(),"متاسفانه در ارتباط با سرور به خطا خورده ایم\n لطفا مجدد تلاش کنید", Toast.LENGTH_LONG).show()
                Toast.makeText(G.context(),t.toString(), Toast.LENGTH_LONG).show()
                callRequest.cancel()
            }
        })
    }
    // function to call server and get company information
    private fun getCompanyInfo() {

        li_loading.visibility = View.VISIBLE
        val api= RetrofitFactory.makeRetrofitService(Constants.baseURL)
        callRequest2 = api.getInfoTouristAttractions("{}")
        callRequest2.enqueue(object : Callback<MainCompanyInfo> {

            override fun onResponse(call: Call<MainCompanyInfo>?, response: Response<MainCompanyInfo>) {
                if (response.isSuccessful ) {
                    if(response.body()!!.status == 1) {
                        val services = response.body()!!.info
                        MySetting().setSetting(services,this@MainActivity)
                        if (Utility.isNetworkConnected(this@MainActivity)){
                            getServices()
                        }else
                            Toast.makeText(G.context(),"اینترنت در دسترس نیست", Toast.LENGTH_LONG).show()
                    }else
                        Toast.makeText(G.context(),"در سرور با مشکلی برخوردیم لطفا بعدا تلاش کنید", Toast.LENGTH_LONG).show()
                }else {
                    Toast.makeText(G.context(), "در ارتباط با سرور به مشکل خوردیم لطفا اینترنت خود را چک کنید", Toast.LENGTH_LONG).show()
                    li_no_internet.visibility = View.VISIBLE
                }
                callRequest2.cancel()
                li_loading.visibility = View.GONE
            }

            override fun onFailure(call: Call<MainCompanyInfo>?, t: Throwable?) {
                li_loading.visibility = View.GONE
//                Toast.makeText(G.context(),"متاسفانه در ارتباط با سرور به خطا خورده ایم\n لطفا مجدد تلاش کنید", Toast.LENGTH_LONG).show()
                Toast.makeText(G.context(),t.toString(), Toast.LENGTH_LONG).show()
                callRequest2.cancel()
            }
        })
    }
}