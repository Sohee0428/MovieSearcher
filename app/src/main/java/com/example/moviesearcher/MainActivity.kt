package com.example.moviesearcher

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.net.URL
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    private val clientId = "client id"
    private val clientSecret = "client secret"

    private val movieTitle: EditText by lazy {
        findViewById(R.id.movieTitle)
    }

    private val searchBtn: Button by lazy {
        findViewById(R.id.searchBtn)
    }

    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.recyclerView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchBtn.setOnClickListener {
            if (movieTitle.text.isEmpty()) {
                return@setOnClickListener
            }

            val layoutManager = LinearLayoutManager(this)

//            리사이클러뷰에 아이템을 배치 후 더이상 보이지 않을 때 재사용성을 결정하는 것
//            불필요한 findViewById를 수행하지 않음
            recyclerView.layoutManager = layoutManager
            recyclerView.setHasFixedSize(true)

            fetJson(movieTitle.text.toString())

//            자판 내리기
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(movieTitle.windowToken, 0)
        }
    }

    private fun fetJson(vararg p0: String) {

        val text = URLEncoder.encode(p0[0], "UTF-8")
        println(text)

        val url =
            URL("https://openapi.naver.com/v1/search/movie.json?query=${text}&display=10&start=1&genre=")

        val formBody = FormBody.Builder()
            .add("query", text)
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
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body()?.string()
                println("Success to execute request : $body")

                val gson = GsonBuilder().create()

                val homefeed = gson.fromJson(body, HomeFeed::class.java)

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