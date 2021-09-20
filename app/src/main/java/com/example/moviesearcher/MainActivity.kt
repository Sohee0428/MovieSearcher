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
import com.example.moviesearcher.data.Item
import com.example.moviesearcher.data.ResultGetSearchMovie
import okhttp3.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var movieList = arrayListOf<Item>()

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


        val adapter = RecyclerViewAdapter(this, movieList)
        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(this)

//            리사이클러뷰에 아이템을 배치 후 더이상 보이지 않을 때 재사용성을 결정하는 것
//            불필요한 findViewById를 수행하지 않음
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        val api = NaverAPI.create()

        searchBtn.setOnClickListener {
            Log.d("searchBtn", "검색버튼 누름")
            if (movieTitle.text.isEmpty()) {
                return@setOnClickListener
            }

//            키보드 숨기기
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(movieTitle.windowToken, 0)

            // TODO 검색 해서 공부하기(api 동기 호출)
//            val response = api.getSearchMovies("test").execute()
//            if (response.isSuccessful) {
//                val body = response.body().toString()
//            }

            // TODO 검색 해서 공부하기(api 비동기 호출)
            api.getSearchMovies(movieTitle.text.toString())
                .enqueue(object : retrofit2.Callback<ResultGetSearchMovie> {
                    override fun onResponse(
                        call: retrofit2.Call<ResultGetSearchMovie>,
                        response: Response<ResultGetSearchMovie>
                    ) {
                        Log.d("jsh", response.body().toString())
                        adapter.addMovieList(response.body()!!.items)
                    }

                    override fun onFailure(
                        call: retrofit2.Call<ResultGetSearchMovie>,
                        t: Throwable
                    ) {
                    }
                })
        }

    }

}