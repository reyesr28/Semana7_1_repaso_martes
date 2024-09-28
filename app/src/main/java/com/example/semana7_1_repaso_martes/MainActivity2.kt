package com.example.semana7_1_repaso_martes

import Beans.tablePost
import DB.AppDataBase
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity2 : AppCompatActivity() {

    lateinit var lista:List<tablePost>
    lateinit var adapter:Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val appDB:AppDataBase by lazy { AppDataBase.getDatabase(this) }
        val recycler:RecyclerView=findViewById(R.id.recyclerPost)
        recycler.layoutManager=LinearLayoutManager(applicationContext)

        try {
            runOnUiThread{
                GlobalScope.launch(Dispatchers.IO) {
                    lista=appDB.postDao().getAll()
                    adapter=Adapter(lista)
                    adapter.notifyDataSetChanged()
                    recycler.adapter=adapter
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
            Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()
        }

    }
}