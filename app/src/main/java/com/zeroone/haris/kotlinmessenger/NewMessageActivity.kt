package com.zeroone.haris.kotlinmessenger

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_new_message.*

class NewMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)
        supportActionBar?.title="Select contact"

        val adapter = GroupAdapter<ViewHolder>()
        newMessage_recview.adapter = adapter

    }
}

class UserItem: Item<ViewHolder>(){

    override fun bind(viewHolder: ViewHolder, position: Int) {
        //TODO: add to list for each user later...
    }
    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }
}
