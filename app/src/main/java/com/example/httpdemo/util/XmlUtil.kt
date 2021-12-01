package com.example.httpdemo.util

import com.example.httpdemo.MyHandler
import okhttp3.OkHttpClient
import okhttp3.Request
import org.xml.sax.InputSource
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.lang.StringBuilder
import javax.xml.parsers.SAXParserFactory
import kotlin.concurrent.thread

object XmlUtil {
    /**
     * xml数据的pull解析方式
     */
    fun parseXMLWithPull(xmlData: String?) :String {
        try {
            //创建XML的pull解析的工厂实例对象
            val xmlPullParser = XmlPullParserFactory.newInstance().newPullParser()
            //将要解析的XML数据交给工厂对象
            xmlPullParser.setInput(StringReader(xmlData))
            var eventType = xmlPullParser.eventType
            var id = ""
            var name = ""
            var version = ""
            var stringBuilder = StringBuilder()
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val nodeName = xmlPullParser.name
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        when (nodeName) {
                            "id" -> id = xmlPullParser.nextText()
                            "name" -> name = xmlPullParser.nextText()
                            "version" -> version = xmlPullParser.nextText()
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if (nodeName == "app") {
                            stringBuilder.append("APP编号：${id}")
                                .append('\n')
                                .append("APP名字：${name}")
                                .append('\n')
                                .append("APP版本：${version}")
                        }
                    }
                }
                eventType = xmlPullParser.next()
            }

            return stringBuilder.toString()
        } catch (e: Exception) {
            return e.printStackTrace().toString()
        }
    }
    fun sendRequestWithOkHttp() {
        thread {
            try {
                val clientService = OkHttpClient()
                val request = Request.Builder()
                    .url("http://10.95.12.201:8080/get_data.xml")
                    .build()
                val response = clientService.newCall(request).execute()
                val responseData = response.body?.string()
                if (responseData != null) {
                    parseXMLWithSAX(responseData)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

     fun parseXMLWithSAX(xmlData: String?) = try {
        //创建XML的SAX解析的工厂实例对象
        val xmlSAXParser = SAXParserFactory.newInstance().newSAXParser()
        //获得SAC解析读入流
        val xmlReader = xmlSAXParser.xmlReader
        //获得我们自己编写的SAX解析算法的实例对象
        val handler = MyHandler()
        //将解析算法设置到SAC解析读入流对象中
        xmlReader.contentHandler = handler
        //开始解析
        xmlReader.parse(InputSource(StringReader(xmlData)))
        handler.getData()
    } catch (e: java.lang.Exception) {
        e.printStackTrace().toString()
    }

}