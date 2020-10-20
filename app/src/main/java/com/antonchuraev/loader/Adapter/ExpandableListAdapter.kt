package com.antonchuraev.loader.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.antonchuraev.loader.DataClasses.Post
import com.antonchuraev.loader.DataClasses.User
import com.antonchuraev.loader.R


class MyExpandableListAdapter(contextP: Context, usersListP: List<User>, postsMapP: MutableMap<Int, MutableList<Post> >) :
    BaseExpandableListAdapter() {

    val context: Context = contextP

    val usersList = usersListP
    val postsMap = postsMapP

    override fun getGroupCount(): Int {
        return usersList.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return postsMap.get(usersList.get(groupPosition).id)!!.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return usersList[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return postsMap.get(usersList.get(groupPosition).id)!!.get(childPosition)
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false;
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView

        if (convertView == null) {
            val inflater = this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.user_info, null)
        }

        val user:User = getGroup(groupPosition) as User

        val nameText = convertView!!.findViewById<TextView>(R.id.name_text)
        val mailText = convertView.findViewById<TextView>(R.id.mail_text)
        val phoneText = convertView.findViewById<TextView>(R.id.phone_text)
        val link = convertView.findViewById<TextView>(R.id.link_text)

        nameText.text = user.name
        mailText.text = user.mail
        phoneText.text = user.phone

        val content = SpannableString(user.link)
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        link.setText(content)
        link.setOnClickListener {
            Log.i("PRESSED", "Pressed Link")
            val url = "http://${user.link}"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        }

        return convertView
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView

        if (convertView == null) {
            val inflater = this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.dropdown_message, null)
        }

        val message:Post = getChild(groupPosition , childPosition) as Post

        val title = convertView!!.findViewById<TextView>(R.id.tittle_message)
        val messageText = convertView.findViewById<TextView>(R.id.message_text)
        title.text = message.tittle
        messageText.text = message.message


        return convertView!!
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

}