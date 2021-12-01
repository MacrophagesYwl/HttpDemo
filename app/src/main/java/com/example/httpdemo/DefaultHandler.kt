package com.example.httpdemo

import android.util.Log
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler
import java.lang.StringBuilder

class MyHandler : DefaultHandler() {
    private var  nodeName =  ""

    private lateinit var id:StringBuilder

    private lateinit var name:StringBuilder

    private lateinit var version:StringBuilder

    private lateinit var data:StringBuilder


    override fun startDocument() {
        id = StringBuilder()
        name = StringBuilder()
        version = StringBuilder()
        data =StringBuilder()
    }

    override fun startElement(
        uri: String,
        localName: String,
        qName: String,
        attributes: Attributes
    ) {
        nodeName = localName
        Log.d("ContentHandler","url is $uri")
        Log.d("ContentHandler","localName is $localName")
        Log.d("ContentHandler","qName is $qName")
        Log.d("ContentHandler","attributes is $attributes")
    }

    override fun characters(ch: CharArray, start: Int, length: Int) {
        when(nodeName){
            "id" -> id.append(ch,start,length)
            "name" -> name.append(ch,start,length)
            "version" -> version.append(ch,start,length)
        }
    }

    override fun endElement(uri: String, localName: String, qName: String) {
        if ("app" == localName){
            Log.d("TAG","APP编号 $id")
            Log.d("TAG","APP名字 $name")
            Log.d("TAG","APP版本 $version")
            data.append("APP编号：${id}")
                    .append('\n')
                    .append("APP名字：${name}")
                    .append('\n')
                    .append("APP版本：${version}")
                    .append('\n').append('\n')

            id.setLength(0)
            name.setLength(0)
            version.setLength(0)
        }
    }

    override fun endDocument() {}

    fun  getData() = data.toString()

}