package com.antonchuraev.loader.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import com.antonchuraev.loader.Adapter.MyExpandableListAdapter
import com.antonchuraev.loader.MVP.Presenter.Presenter
import com.antonchuraev.loader.MVP.ViewInterface
import com.antonchuraev.loader.DataClasses.Post
import com.antonchuraev.loader.DataClasses.User
import com.antonchuraev.loader.R


class AllUsersFragment : Fragment() , ViewInterface {


    companion object {
        fun newInstance(usersInputList: List<User>):AllUsersFragment{
            val newFragment = AllUsersFragment()
            newFragment.usersList=usersInputList
            return newFragment
        }
    }

    lateinit var usersList:List<User>

    lateinit var listAdapter: ExpandableListAdapter
    lateinit var expListView: ExpandableListView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v:View = inflater.inflate(R.layout.fragment_all_users, container, false)

        expListView =  v.findViewById(R.id.list_view);

        val presenter = Presenter(this)
        presenter.startLoadingPosts()

        return v
    }



    override fun showLoadResult(response: Any?) {
        if (response==null){
            throw Exception("Loading Error")
        }

        listAdapter = MyExpandableListAdapter(context!!, usersList, response as MutableMap<Int, MutableList<Post> >)
        expListView.setAdapter(listAdapter)
        expListView.setIndicatorBounds(0, 2100) //TODO Dropdown button location

    }


}