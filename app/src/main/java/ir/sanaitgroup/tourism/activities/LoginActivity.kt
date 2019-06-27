package ir.sanaitgroup.tourism.activities

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ir.icegroup.utilities.Utility
import ir.sanaitgroup.tourism.*
import ir.sanaitgroup.tourism.utils.Constants
import ir.sanaitgroup.tourism.utils.G
import ir.sanaitgroup.tourism.rest.RetrofitFactory
import kotlinx.android.synthetic.main.activity_login.*
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import ir.sanaitgroup.tourism.models.MainLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity :AppCompatActivity(){

    private var password = ""
    private var username = ""
    private lateinit var callRequest: Call<MainLogin>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initListeners()
        setDefaultValue()
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
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (::callRequest.isInitialized) {
                callRequest.cancel()
                Toast.makeText(G.context(),"cancel is done",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(G.context(),"cancel is not done",Toast.LENGTH_SHORT).show()
            }
            return super.onKeyDown(keyCode, event)
        }
        return false
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


    private fun setDefaultValue(){


    }
    private fun initListeners(){
        txt_forget_pass.setOnClickListener {
            Toast.makeText(this, "رمز عبور شما 123456 است", Toast.LENGTH_SHORT).show()
        }

        btn_login.setOnClickListener {
            username = txt_Username.text.toString()
            password = txt_Password.text.toString()

            Utility.closeKeyboard(this)

            if (username.isEmpty())
                Toast.makeText(G.context(), getString(R.string.message_login_username_error), Toast.LENGTH_LONG).show()
            else {
                if (password.isEmpty()) {
                    Toast.makeText(G.context(), getString(R.string.message_login_password_error), Toast.LENGTH_LONG).show()
                } else {
                    if (Utility.isNetworkConnected(this)) {
                        if (::callRequest.isInitialized)
                            callRequest.cancel()

                        sendUserLogin()
                    } else {
                        Toast.makeText(G.context(), "اینترنت در دسترس نیست", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        txt_Password.setOnEditorActionListener{ _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                btn_login.callOnClick()
                true
            } else {
                false
            }
        }
    }

    // function to call server and do login
    private fun sendUserLogin() {

        login_progress_bar.visibility = View.VISIBLE
        val api=
            RetrofitFactory.makeRetrofitService(Constants.baseURL + "search/")
        callRequest = api.getUsers("{username,password}")
        callRequest.enqueue(object : Callback<MainLogin> {

            override fun onResponse(call: Call<MainLogin>?, response: Response<MainLogin>) {
                if (response.isSuccessful ) {
                    val users = response.body()
                    val user = users?.users
                    val length = user!!.size

                    var str = ""
                    for (i in 0 until length) {
                        str = str + "\n" + user[i].id + " " + user[i].gmap
                    }

                    txt_users!!.text = str
                }else
                    Toast.makeText(G.context(),"خطا",Toast.LENGTH_LONG).show()
                callRequest.cancel()
                login_progress_bar.visibility = View.GONE
            }

            override fun onFailure(call: Call<MainLogin>?, t: Throwable?) {
//                Toast.makeText(G.context(),"Error: " + t.toString(),Toast.LENGTH_LONG).show()
                login_progress_bar.visibility = View.GONE
                Toast.makeText(G.context(),"متاسفانه در ارتباط با سرور به خطا خورده ایم\n لطفا مجدد تلاش کنید",Toast.LENGTH_LONG).show()
                callRequest.cancel()
            }
        })
    }

}