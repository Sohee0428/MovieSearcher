package com.example.moviesearcher

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearcher.data.ResultGetSearchMovie
import okhttp3.*
import retrofit2.Response
import com.example.moviesearcher.data.Item as DataItem

class MainActivity : AppCompatActivity() {

    var movieList = arrayListOf<DataItem>()

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
            Log.d("searchBtn", "검색버튼 누름")
            if (movieTitle.text.isEmpty()) {
                return@setOnClickListener
            }

            val adapter = RecyclerViewAdapter(this, movieList)
            recyclerView.adapter = adapter

            val layoutManager = LinearLayoutManager(this)

//            리사이클러뷰에 아이템을 배치 후 더이상 보이지 않을 때 재사용성을 결정하는 것
//            불필요한 findViewById를 수행하지 않음
            recyclerView.layoutManager = layoutManager
            recyclerView.setHasFixedSize(true)

//            키보드 숨기기
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(movieTitle.windowToken, 0)
        }

        val api = NaverAPI.create()
        api.getSearchMovies(movieTitle).enqueue(object : retrofit2.Callback<ResultGetSearchMovie> {
            override fun onResponse(
                call: retrofit2.Call<ResultGetSearchMovie>,
                response: Response<ResultGetSearchMovie>
            ) {

            }

            override fun onFailure(call: retrofit2.Call<ResultGetSearchMovie>, t: Throwable) {
            }
        })

        val thread = Thread{
            var apiExamSearchBlog = ApiExamSearchBlog()
            apiExamSearchBlog.main()
        }.start()

    }

}