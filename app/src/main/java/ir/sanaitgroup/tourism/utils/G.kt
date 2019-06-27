package ir.sanaitgroup.tourism.utils

import android.content.Context
import androidx.multidex.MultiDexApplication
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import ir.sanaitgroup.tourism.R


const val appCode ="tourism"

class G : MultiDexApplication(){

    init {
        instance = this
    }
    companion object {
        private var instance: G? = null

        fun context() : Context {
            return instance!!.applicationContext
        }

        fun getAppCode(): String{
            return appCode
        }
    }

    override fun onCreate() {
        super.onCreate()

        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath(resources.getString(R.string.myFont))
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
    }
}