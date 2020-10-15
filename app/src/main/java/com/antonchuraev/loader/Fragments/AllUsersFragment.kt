package com.antonchuraev.loader.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper

import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import com.antonchuraev.loader.Adapter.MyExpandableListAdapter
import com.antonchuraev.loader.Model.MessageModel
import com.antonchuraev.loader.Model.MessagesDB
import com.antonchuraev.loader.Model.UserModel
import com.antonchuraev.loader.R
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class AllUsersFragment : Fragment() {


    companion object {
        fun newInstance(usersInputList: List<UserModel>):AllUsersFragment{
            val newFragment = AllUsersFragment()
            newFragment.usersList=usersInputList
            return newFragment
        }
    }

    lateinit var usersList:List<UserModel>

    lateinit var listAdapter: ExpandableListAdapter
    lateinit var expListView: ExpandableListView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v:View = inflater.inflate(R.layout.fragment_all_users, container, false)

        expListView =  v.findViewById(R.id.list_view);

        loadMessages()

        return v
    }

    private fun loadMessages(): MutableMap<UserModel, List<MessageModel>> {
        val messageMap = mutableMapOf<UserModel, List<MessageModel> >()

        var addList = mutableListOf<MessageModel>()

        val messages_URL="https://jsonplaceholder.typicode.com/posts"
        val request: Request = Request.Builder().url(messages_URL).build()
        val okHttpClient=OkHttpClient()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {

                val jsonData: String? = response.body()?.string()
                val Jarray = JSONArray(jsonData)


                Log.i("RRR",Jarray.length().toString())

                val addedList = mutableListOf<MessageModel>()
                for (i in 0 until Jarray.length()) {
                    val Jobj: JSONObject = Jarray.get(i) as JSONObject

                    Log.i("TAG", Jobj.toString())

                    val tittle = Jobj.get("title").toString()
                    val body = Jobj.get("body").toString()
                    val userId = Jobj.get("userId")

                    val message = MessageModel(userId as Int, tittle, body)
                    addedList.add(message)
                }
                Log.i("RRR",messageMap.toString())
                val bd=MessagesDB(addedList)
                setNewAdapter(bd)

            }


        })


        return messageMap

    }

    private fun setNewAdapter( bd: MessagesDB) {

        val mHandler =  Handler(Looper.getMainLooper())
        mHandler.post {
            listAdapter = MyExpandableListAdapter(context!!, usersList, bd)

            expListView.setAdapter(listAdapter)
            expListView.setIndicatorBounds(0, 2100) //TODO
        }

    }



}