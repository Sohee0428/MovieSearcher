package com.example.moviesearcher

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.net.URL
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    val clientId = "client id"
    val clientSecret = "client secret"

    private val movieTitle: EditText by lazy {
        findViewById<EditText>(R.id.movieTitle)
    }

    private val searchBtn: Button by lazy {
        findViewById<Button>(R.id.searchBtn)
    }

    private val recyclerView: RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.recyclerView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchBtn.setOnClickListener {

            if (movieTitle.text.isEmpty()) {
                return@setOnClickListener
            }

            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            recyclerView.setHasFixedSize(true)

            fetJson(movieTitle.text.toString())

            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(movieTitle.windowToken, 0)
        }
    }

    private fun fetJson(vararg p0: String){

        val text = URLEncoder.encode("${p0[0]}", "UTF-8")
        println(text)

        val url = URL("https://openapi.naver.com/v1/search/movie.json?query=${text}&display=10&start=1&genre=")

        val formBody = FormBody.Builder()
            .add("query","${text}")
            .add("display", "10")
            .add("start", "1")
            .add("genre", "1")
            .build()

        val request = Request.Builder()
            .url(url)
            .addHeader("X-Naver-Client-Id", clientId)
            .addHeader("X-Naver-Client-Secret", clientSecret)
            .method("Get", null)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback{
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body()?.string()
                println("Success to execute request : $body")

                val gson = GsonBuilder().create()

                val homefeed = gson.fromJson(body, Homefeed::class.java)

                runOnUiThread {
                    recyclerView.adapter = RecyclerViewAdapter(homefeed)
                    movieTitle.setText("")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })
    }
}