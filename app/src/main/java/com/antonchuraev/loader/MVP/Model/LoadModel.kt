package com.antonchuraev.loader.MVP.Model

import android.os.AsyncTask
import com.antonchuraev.loader.DataClasses.Post
import com.antonchuraev.loader.DataClasses.User
import okhttp3.*
import org.json.JSONArray

class LoadModel:com.antonchuraev.loader.MVP.ModelInterface {

    val users_URL="https://jsonplaceholder.typicode.com/users"
    val posts_URL="https://jsonplaceholder.typicode.com/posts"


    override fun loadUsers(): MutableList<User>? {
        return parseJSONtoUserModelList(loadFromUrl(users_URL))
    }

    private fun parseJSONtoUserModelList(response: String?): MutableList<User>? {
        if (response==null){
            return null
        }

        var usersList = mutableListOf<User>()

        val Jarray = JSONArray(response)
        for (i in 0 until  Jarray.length()){
            val item = Jarray.getJSONObject(i)

            val user = User(id = item.get("id") as Int, name = item.getString("name") , mail = item.getString("email") , phone = item.getString("phone") , link = item.getString("website") )

            usersList.add(user)
        }

        return usersList
    }

    override fun loadPosts(): MutableMap<Int, MutableList<Post>>? {
        return parseJSONtoPostModelList(loadFromUrl(posts_URL))
    }

    private fun parseJSONtoPostModelList(response: String?): MutableMap<Int, MutableList<Post>>? {
        if (response==null){
            return null
        }

        val postsList: MutableMap<Int, MutableList<Post> > = mutableMapOf()

        for (i in 1..10){
            postsList.put(i, mutableListOf() )
        }


        val Jarray = JSONArray(response)
        for (i in 0 until  Jarray.length()){
            val item = Jarray.getJSONObject(i)

            val post = Post(userId = item.get("userId") as Int, tittle = item.getString("title") , message = item.getString("body") )
            postsList.get(item.get("userId"))?.add(post)
        }


        return postsList
    }

    private fun loadFromUrl(url: String): String? {
        val asyncLoader = AsyncLoader()
        asyncLoader.execute(url)

        if (asyncLoader.get()==null){
            return null
        }

        return asyncLoader.get()
    }


}

class AsyncLoader:AsyncTask<String , String , String>(){

    override fun doInBackground(vararg params: String?): String? {

        val okHttpClient = OkHttpClient()

        val request: Request = Request.Builder().url(params[0]).build()

        try {
            val response = okHttpClient.newCall(request).execute()

            return response.body()!!.string()
        }catch (e:Exception){
            e.printStackTrace();
        }
        return null
    }

}
