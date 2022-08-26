package com.udacity

import android.os.Bundle

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    lateinit var tvName: TextView
    lateinit var tv_status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        tvName = findViewById(R.id.tv_file_name)
        tv_status = findViewById(R.id.tv_status)
        tv_status.text = intent.getStringExtra("status")
        tvName.text = intent.getStringExtra("fileName")


    }


}
