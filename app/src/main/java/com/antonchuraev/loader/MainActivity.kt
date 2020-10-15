package com.antonchuraev.loader

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import com.antonchuraev.loader.Fragments.StartFragment

class MainActivity : AppCompatActivity() {
    val TAG="TAG"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createAndShowStartFragment()

    }

    private fun createAndShowStartFragment() {
        val newFragment = StartFragment.newInstance()
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.fragment_container, newFragment, TAG)
        fragmentTransaction.commit()
    }

}