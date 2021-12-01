package com.example.httpdemo.util

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

object HttpURLConnectionUtil:HttpService {
    override fun get(urlString: String): String {
        val stringBuilder = StringBuilder()
        //开启线程,发起网络请求
        var connection: HttpURLConnection? = null
        try {
            //根据网址创建URL对象
            val url = URL(urlString)
            //打开网址链接
            connection = url.openConnection() as HttpURLConnection
            //设置连接超时
            connection.connectTimeout = 8000
            //设置读取超时
            connection.readTimeout = 8000
            //设置请求方式为POST（默认是GET）
            //connection.requestMethod = "POST"
            //写入参数
            //获取网络请求(服务器返回)的输入流
            val input = connection.inputStream
            //对输入流进行读取
            val reader = BufferedReader(InputStreamReader(input))

            reader.use {
                reader.forEachLine {
                    stringBuilder.append(it)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            //将连接关闭
            connection?.disconnect()
        }
        return stringBuilder.toString()
    }


    override fun post(urlString: String, parameter: String): String {
        val stringBuilder = StringBuilder()
        var connection: HttpURLConnection? = null
        //开启线程,发起网络请求
        thread {
            var connection: HttpURLConnection? = null
            try {
                //根据网址创建URL对象
                val url = URL(urlString)
                //打开网址链接
                connection = url.openConnection() as HttpURLConnection
                //设置连接超时
                connection.connectTimeout = 8000
                //设置读取超时
                connection.readTimeout = 8000
                //设置请求方式为POST（默认是GET）
                connection.requestMethod = "POST"
                //写入参数
                val output = DataOutputStream(connection.outputStream)
                //获取网络请求(服务器返回)的输入流
                val input = connection.inputStream
                //对输入流进行读取
                val reader = BufferedReader(InputStreamReader(input))
                //将读到的数据拼接为字符串
                reader.use {
                    reader.forEachLine {
                        stringBuilder.append(it)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                //将连接关闭
                connection?.disconnect()
            }
        }
        return stringBuilder.toString()
    }
}
