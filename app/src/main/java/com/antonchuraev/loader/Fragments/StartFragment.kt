package com.antonchuraev.loader.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.antonchuraev.loader.MVP.Presenter.Presenter
import com.antonchuraev.loader.MVP.ViewInterface
import com.antonchuraev.loader.DataClasses.User
import com.antonchuraev.loader.R


class StartFragment: Fragment() , ViewInterface {

    companion object{
        fun newInstance(): StartFragment{
            return StartFragment()
        }
    }

    lateinit var loadButton:Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v:View = inflater.inflate(R.layout.start_fragment, container, false)

        loadButton=v.findViewById(R.id.load_button)

        val presenter = Presenter(this)

        loadButton.setOnClickListener {
            Log.i("TAG", "pressed load button")
            presenter.startLoadingUsersButtonClicked()
        }

        return v
    }

    private fun startAllUsersFragment(newList: MutableList<User>) {
        val newFragment = AllUsersFragment.newInstance(newList)
        val fragmentTransaction: FragmentTransaction = (activity as AppCompatActivity).supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.fragment_container, newFragment, "TAG")
        fragmentTransaction.commit()
    }

    override fun showLoadResult(response: Any?) {

        if (response==null){
            loadButton.setText("ERROR!\nClick to Restart")
        }
        else{
            startAllUsersFragment(response as MutableList<User> )
        }

    }

}