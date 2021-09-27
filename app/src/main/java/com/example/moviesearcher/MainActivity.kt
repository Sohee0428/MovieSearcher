package com.example.moviesearcher

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesearcher.data.Item
import com.example.moviesearcher.data.ResultGetSearchMovie
import com.example.moviesearcher.databinding.ActivityMainBinding
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val isVisible = false
    var movieList = arrayListOf<Item>()
    val api = NaverAPI.create()
    lateinit var activityDataBinding: ActivityMainBinding
    val adapter: RecyclerViewAdapter by lazy {
        RecyclerViewAdapter(this, movieList)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityDataBinding.activity = this
        activityDataBinding.recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(this)

//            리사이클러뷰에 아이템을 배치 후 더이상 보이지 않을 때 재사용성을 결정하는 것
//            불필요한 findViewById를 수행하지 않음
        activityDataBinding.recyclerView.layoutManager = layoutManager
        activityDataBinding.recyclerView.setHasFixedSize(true)

    }

    fun clickBtn() {
        Log.d("searchBtn", "검색버튼 누름")
        if (activityDataBinding.movieTitle.text.isEmpty()) {
            return
        }

//            키보드 숨기기
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activityDataBinding.movieTitle.windowToken, 0)

        // TODO 검색 해서 공부하기(api 동기 호출)
//            val response = api.getSearchMovies("test").execute()
//            if (response.isSuccessful) {
//                val body = response.body().toString()
//            }

        // TODO 검색 해서 공부하기(api 비동기 호출)
        api.getSearchMovies(activityDataBinding.movieTitle.text.toString())
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