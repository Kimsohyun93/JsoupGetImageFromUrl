package com.example.myapplication

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class GetImageFromUrl(private val url: String) {

    fun getImage(): String? {
        return getImage(url)
    }

    fun getImage(mUrl: String?): String? {
        var result: String? = null
        val job = CoroutineScope(Dispatchers.IO).launch {
            try {
                val con: org.jsoup.Connection = Jsoup.connect(mUrl)
                val doc: Document = con.get()
                var img: String = ""
                val imgs: Elements = doc.getElementsByTag("img")
Log.e("document","$doc")

Log.e("Count","${imgs.size}")
                for (imageElement in imgs) {
                    Log.e("imageElement null", "$imageElement")
                    if (imageElement != null) {
                        //for each element get the srs url
                        img = imageElement.absUrl("src")
                        if (!img.contains("doubleclick.net") &&
                            !img.contains("feedburner.com") &&
                            !img.contains("feedsportal.com") &&
                            !img.contains("ads") &&
                            !img.contains("img_blank")
                        ) {
                            result = img
                            break
                        }
                    }
                }


//                val elemImages = doc.select("img[src$=.jpg]")
//                for (e in elemImages) {
//                    Log.d("Single Image", e.absUrl("src"))
//                }
//
//
//                Log.e("document : ","${doc.select("img[src$=.jpg]")[0]}")
//                result = doc.select("img[src$=.jpg]")[0].attr("content")
//                Log.e("result : ","result : $result")
            } catch (e: Exception) {
                Log.e("Error : ", "${e.message}")
            }
        }
        runBlocking {
            job.join()
        }
        Log.e("mUrl", "$mUrl : $result")
        return result
    }
}