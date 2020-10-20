package com.antonchuraev.loader.MVP

import com.antonchuraev.loader.DataClasses.Post
import com.antonchuraev.loader.DataClasses.User

interface ViewInterface{
    fun showLoadResult(response:Any?)
}

interface PresenterInterface {
    fun startLoadingUsersButtonClicked()
    fun startLoadingPosts()
}

interface ModelInterface {
    fun loadUsers(): MutableList<User>?
    fun loadPosts(): MutableMap<Int , MutableList<Post>>?
}
