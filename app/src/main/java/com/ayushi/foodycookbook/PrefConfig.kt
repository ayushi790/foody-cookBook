package com.ayushi.foodycookbook

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

open class PrefConfig {
    val LIST_KEY = "list_key";
    public fun writeListInPref(context: Context, list:ArrayList<Meal>) {

        val gson = Gson()
        val jsonstring = gson.toJson(list)

        val pref:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putString(LIST_KEY, jsonstring).apply()
    }

    fun readListFromPref(context:Context): ArrayList<Meal>{
        try{
            val pref:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val jsonstring = pref.getString(LIST_KEY,"")

            val gson = Gson()
            val type = object : TypeToken<ArrayList<Meal?>?>() {}.type
            val list: ArrayList<Meal> = gson.fromJson(jsonstring, type)
            return list
        }catch (e: NullPointerException){
            val list = arrayListOf<Meal>()
            return list
        }
    }
}