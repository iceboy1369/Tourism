package ir.sanaitgroup.tourism.utils

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import ir.sanaitgroup.tourism.models.MainCompanyInfo

class MySetting {

    internal fun setUser(user: String, activity: Activity) {
        val editor = activity.getSharedPreferences(G.getAppCode(), MODE_PRIVATE).edit()
        editor.putString("Username", user)
        editor.apply()
    }

    internal fun getUser(activity : Activity) : String?{
        val prefs = activity.getSharedPreferences(G.getAppCode(), MODE_PRIVATE)
        return prefs.getString("Username", "")
    }

    internal fun setSetting(service: MainCompanyInfo.Info?, activity: Activity) {
        val editor = activity.getSharedPreferences(G.getAppCode(), MODE_PRIVATE).edit()
        editor.putString("gmap", service?.gmap)
        editor.putString("hashtag", service?.hashtags)
        editor.putString("name", service?.name)
        editor.putString("url", service?.url)
        editor.apply()
    }

    internal fun getSetting(activity : Activity) : MainCompanyInfo.Info {
        val prefs = activity.getSharedPreferences(G.getAppCode(), MODE_PRIVATE)
        val gmap = prefs.getString("gmap", "")
        val url = prefs.getString("url", "")
        val name = prefs.getString("name", "")
        val hashtags = prefs.getString("hashtag", "")
        return MainCompanyInfo.Info(1,name,hashtags,url,gmap)
    }

}