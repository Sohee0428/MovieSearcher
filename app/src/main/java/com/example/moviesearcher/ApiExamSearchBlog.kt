//package com.example.moviesearcher

//TODO OKHttp 이전의 자바버전

//
//import android.graphics.ColorSpace.connect
//import org.json.JSONException
//import org.json.JSONObject
//import java.io.*
//import java.lang.reflect.Array.get
//import java.net.HttpURLConnection
//import java.net.MalformedURLException
//import java.net.URL
//import java.net.URLEncoder
//
//class ApiExamSearchBlog {
//
//    val clientId = "7HOJNcmQsYMZjMHDndyK"
//    val clientSecret = "XOB__trP_C"
//
//    fun main(){
//        var text: String? = null
//        try {
//            text = URLEncoder.encode("그린팩토리","UTF-8")
//        }catch (e: UnsupportedEncodingException) {
//            throw RuntimeException("검색어 인코딩 실패", e)
//        }
//        val apiURL = "https://openapi.naver.com/v1/search/movie" + text!!
//
//        val requestHeaders: HashMap<String, String> = HashMap()
//        requestHeaders.put("X-Naver-Client-Id", clientId)
//        requestHeaders.put("X-Naver-Client-Secret", clientSecret)
//        val responseBody = get(apiURL, requestHeaders)
//
//        parseData(responseBody)
//    }
//
//    private operator fun get(apiUrl: String, requestHeaders: Map<String, String>): String {
//        val con = connect(apiUrl)
//        try{
//            con.setRequestMethod("GET")
//            for((key, value) in requestHeaders) {
//                con.setRequestProperty(key, value)
//            }
//            val responseCode = con.getResponseCode()
//            return if (responseCode == HttpURLConnection.HTTP_OK) {
//                readBody(con.getInputSteam())
//            }else {
//                readBody(con.getErrorSteam())
//            }
//        } catch(e: IOException {
//            throw RuntimeException("API 요청과 응답 실패", e)
//        }finally {
//            con.disconnect()
//        }
//    }
//    private fun connect(apiUrl: String): HttpURLConnection{
//        try {
//            val url = URL(apiUrl)
//            return url.openConnection() as HttpURLConnection
//        } catch (e: MalformedURLException) {
//            throw RuntimeException("API URL이 잘못되었습니다 : $apiUrl", e)
//        } catch (e: IOException){
//            throw java.lang.RuntimeException("연결에 실패했습니다: $apiUrl", e)
//        }
//    }
//    private fun readBody(body: InputStream): String {
//        val streamReader = InputStreamReader(body)
//
//        try {
//            BufferedReader(streamReader).use { lineReader ->
//                val responseBody = StringBuilder()
//
//                var line: String? = lineReader.readLine()
//                while (line != null) {
//                    responseBody.append(line)
//                    line = lineReader.readLine()
//                }
//                return responseBody.toString()
//            }
//        }catch (e: IOException){
//            throw RuntimeException("API 응답을 읽는데 실패했습니다.",e)
//        }
//    }
//    private fun parseData(responseBody: String){
//        var title: String
//        var jsonObject: JSONObject? = null
//        try {
//            jsonObject = JSONObject(responseBody)
//            val jsonArray = jsonObject.getJSONArray("items")
//
//            for (i in 0 until jsonArray.length()) {
//                val item = jsonArray.getJSONObject(i)
//                title = item.getString("title")
//                println("TITLE: $title")
//            }
//        }catch (e: JSONException){
//            e.printStackTrace()
//        }
//
//    }
//}