package com.example.semana7_1_repaso_martes

import Beans.Post
import Beans.tablePost
import DB.AppDataBase
import PlaceHolder.PlaceHolderApi
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    lateinit var service:PlaceHolderApi
    lateinit var post:Post
    private lateinit var appDb:AppDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        appDb=AppDataBase.getDatabase(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service= retrofit.create<PlaceHolderApi>(PlaceHolderApi::class.java)

        val txtId:EditText=findViewById(R.id.txtId)
        val btnConsultar:Button=findViewById(R.id.btnConsultar)

        btnConsultar.setOnClickListener(){
            getPostId(txtId.text.toString().toInt())
            txtId.text.clear()
        }

        val btnGrabar:Button=findViewById(R.id.btnGuardar)

        btnGrabar.setOnClickListener() {
            val regPost=tablePost(post.id,post.userId,post.title,post.body)

            GlobalScope.launch (Dispatchers.IO) {
                appDb.postDao().insert(regPost)

            }
        }

        val btnListar:Button=findViewById(R.id.btnListar)
        btnListar.setOnClickListener(){
            val intent=Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }


    }

    fun getPostId(id:Int){
        service.getPostId(id).enqueue(object :Callback<Post>{
            override fun onResponse(p0: Call<Post>, p1: Response<Post>) {

                post=p1?.body()!!

                val txtRes:TextView=findViewById(R.id.txtRes)

                txtRes.text="ID: "+post?.id.toString()+"\n"+
                        "userID: "+post?.userId.toString()+"\n"+
                        "Title: "+post?.title.toString()+"\n"+
                        "Body: "+post?.body.toString()+"\n"
            }

            override fun onFailure(p0: Call<Post>, p1: Throwable) {
                p1?.printStackTrace()
            }

        })
    }
}