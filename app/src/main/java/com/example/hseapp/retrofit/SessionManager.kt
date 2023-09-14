package com.example.hseapp.retrofit

import com.example.hseapp.R

import android.content.Context
import android.content.SharedPreferences
import java.sql.Types.NULL


class SessionManager(context: Context){
    val PRIVATE_MODE = 0
    private val IS_LOGIN = "is_login"
    private var prefs: SharedPreferences=context.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor? = prefs?.edit()
    companion object{
        const val akses_token="Token"
    }

    fun saveAuthToken(token:String){
        val editor=prefs.edit()
        editor.putString(akses_token,token)
        editor.apply()
    }
    fun saveId(Id:Int){
        val editor=prefs.edit()
        editor.putInt("Id",Id)
        editor.apply()
    }
    fun getid(): Int? {
        return prefs?.getInt("Id", NULL)
    }
    fun fetchAuthToken():String?{
        return prefs.getString(akses_token,null)
    }
    fun setLoggin(isLogin: Boolean) {
        editor?.putBoolean(IS_LOGIN, isLogin)
        editor?.commit()
    }

    fun isLogin(): Boolean? {
        return prefs?.getBoolean(IS_LOGIN, false)
    }
    fun removeData() {
        editor?.clear()
        editor?.commit()
    }
}