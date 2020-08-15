package com.vitaz.decider.Utilities

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class SharedPrefs(context: Context) {

    val PREFS_FILENAME = "prefs"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)
    val ITEM_LIST = "itemList"

    val defaultList = arrayListOf("Watch TV", "Save the world!", "Make a photo of Spider Man")
    val gsonDefaultList = Gson()
    val jsonDefaultList: String = gsonDefaultList.toJson(defaultList)


    fun saveList(list: ArrayList<String>) {
        val gson = Gson()
        val json: String = gson.toJson(list)
        prefs.edit().putString(ITEM_LIST, json).apply()
    }

    fun getList() : ArrayList<String> {
        val gson = Gson()
        val json: String? = prefs.getString(ITEM_LIST, jsonDefaultList)
        return gson.fromJson<ArrayList<String>>(json, ArrayList::class.java)
    }
}