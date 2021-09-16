package com.example.moviesearcher.data

data class ResultGetSearchMovie(
        var lastBuildData: String = "",
        var total: Int = 0,
        var start: Int = 0,
        var display: Int = 0,
        var items: List<Item>

) {
}