package com.example.moviesearcher.data

import com.google.gson.annotations.SerializedName

data class ResultGetSearchMovie(
        @SerializedName("lastBuildData")
        var lastBuildData: String = "",
        @SerializedName("total")
        var total: Int = 0,
        @SerializedName("start")
        var start: Int = 0,
        @SerializedName("display")
        var display: Int = 0,
        @SerializedName("items")
        var items: List<Item>
)