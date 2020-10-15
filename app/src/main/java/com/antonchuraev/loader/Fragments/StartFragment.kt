package com.antonchuraev.loader.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.antonchuraev.loader.Model.UserModel
import com.antonchuraev.loader.R
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class StartFragment: Fragment() {

    companion object{
        fun newInstance(): StartFragment{
            return StartFragment()
        }
    }

    val users_URL="https://jsonplaceholder.typicode.com/users"
    lateinit var okHttpClient:OkHttpClient

    lateinit var loadButton:Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v:View = inflater.inflate(R.layout.start_fragment, container, false)

        okHttpClient = OkHttpClient()

        loadButton=v.findViewById(R.id.load_button)
        loadButton.setOnClickListener {
            Log.i("TAG", "pressed load button")

            loadAndShowUsers()
        }

        return v
    }

    private  fun loadAndShowUsers(){

        val mHandler =  Handler(Looper.getMainLooper());

        val newList:MutableList<UserModel> = mutableListOf()

        val request: Request = Request.Builder().url(users_URL).build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                mHandler.post {
                    loadButton.text = "Error!\nClick to restart"
                }
            }

            override fun onResponse(call: Call, response: Response) {

                val jsonData: String? = response.body()?.string()
                val Jarray = JSONArray(jsonData)

                for (i in 0 until Jarray.length()) {
                    val Jobj: JSONObject = Jarray.get(i) as JSONObject

                    val name = Jobj.get("name").toString()
                    val email = Jobj.get("email").toString()
                    val phone = Jobj.get("phone").toString()
                    val site = Jobj.get("website").toString()
                    val id = Jobj.get("id")

                    val user = UserModel(id as Int,name, email, phone, site)
                    newList.add(user)
                }

                Log.i("TAG", "success onResponse newList.size:${newList.size}")
                startAllUsersFragment(newList)

            }


        })




    }

    private fun startAllUsersFragment(newList: MutableList<UserModel>) {
        val newFragment = AllUsersFragment.newInstance(newList)
        val fragmentTransaction: FragmentTransaction = (activity as AppCompatActivity).supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.fragment_container, newFragment, "TAG")
        fragmentTransaction.commit()
    }


}