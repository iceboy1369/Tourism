package ir.sanaitgroup.tourism.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import ir.sanaitgroup.tourism.R
import kotlinx.android.synthetic.main.activity_welcome.*

class Welcome: AppCompatActivity(){

    private var isBackPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_welcome)

        gifView.setImageResource(R.drawable.sayna)
        gifView.setFreezesAnimation(true)

//        val user = MySetting().getUser(this)
//        if (user!!.isEmpty()) {
//            val intent = Intent(this, LoginActivity::class.java)
//            finish()
//            startActivity(intent)
//        }else {
            goForMainActivity()
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        overridePendingTransitionExit()
    }
    override fun onStart() {
        super.onStart()
        overridePendingTransitionEnter()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        isBackPressed = true
        return super.onKeyDown(keyCode, event)
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

    // wait 3 second then go to main activity
    private fun goForMainActivity() {
        val timeout = Handler()
        timeout.postDelayed({
            if (!isBackPressed) {
                progress_bar.visibility = View.GONE
                val startMainActivity = Intent(this, MainActivity::class.java)
                startActivity(startMainActivity)
            }
            finish()
        }, 3000)
    }
}