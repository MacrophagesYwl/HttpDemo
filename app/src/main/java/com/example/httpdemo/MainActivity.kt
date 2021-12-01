package com.example.httpdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.TextView
import com.example.httpdemo.databinding.ActivityMainBinding
import com.example.httpdemo.util.HttpService
import com.example.httpdemo.util.HttpURLConnectionUtil
import com.example.httpdemo.util.HttpUtil
import com.example.httpdemo.util.XmlUtil
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.xml.sax.InputSource
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.io.StringReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.URL
import java.net.URLEncoder
import javax.net.ssl.HttpsURLConnection
import javax.xml.parsers.SAXParserFactory
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var requestArray : Array<String>
//    private val  dataTypeArray = resources.getStringArray(R.array.arr_dara_type)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        init()



        mBinding.btnGet.setOnClickListener {
           val data = HttpUtil.getHttpService().get("http://10.95.12.201:8080/login?username=admin&password=123456")

        }

        mBinding.btnPost.setOnClickListener{
             thread {
                try {
                    //创建OkHttp客户端对象
                    val client = OkHttpClient()
                    //创建Request对象，用来发送HTTP请求
                    val request = Request.Builder()
                        .url("http://10.95.12.201:8080/xml/get_data.xml")
                        .build()
                    //发出网络请求，并接受回传的数据
                    val response = client.newCall(request).execute()
                    //数据解析出来
                    val data = response.body?.string()
                    runOnUiThread {
                        mBinding.textView.text =  XmlUtil.parseXMLWithPull(data)
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }
        }

        mBinding.btnGetOkhttp.setOnClickListener {
            thread {
                try {
                    //创建OkHttp客户端对象
                    val client = OkHttpClient()
                    val requestBody = FormBody.Builder()
                        .add("id","admin")
                        .add("name","张三")
                        .build()
                    //创建Request对象，用来发送HTTP请求
                    val request = Request.Builder()
                        .url("http://10.95.12.201:8080/get")
                        .post(requestBody)
                        .build()
                    //发出网络请求，并接受回传的数据
                    val response = client.newCall(request).execute()
                    //数据解析出来
                    val data = response.body?.string()
                    runOnUiThread {
                        mBinding.textView.text =  data
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }
        }

        mBinding.btnPostOkhttp.setOnClickListener {
            thread {
                try {
                    //创建OkHttp客户端对象
                    val client = OkHttpClient()
                    val requestBody = FormBody.Builder()
                        .add("id","admin")
                        .add("name","张三")
                        .build()
                    //创建Request对象，用来发送HTTP请求
                    val request = Request.Builder()
                        .url("http://10.95.12.201:8080/post/student")
                        .post(requestBody)
                        .build()
                    //发出网络请求，并接受回传的数据
                    val response = client.newCall(request).execute()
                    //数据解析出来
                    val data = response.body?.string()
                    runOnUiThread {
                        mBinding.textView.text =  data
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }
        }

        mBinding.btnXmlPull.setOnClickListener {
            thread {
                try {
                    //创建OkHttp客户端对象
                    val client = OkHttpClient()
                    //创建Request对象，用来发送HTTP请求
                    val request = Request.Builder()
                        .url("http://10.95.12.201:8080/xml/get_data.xml")
                        .build()
                    //发出网络请求，并接受回传的数据
                    val response = client.newCall(request).execute()
                    //数据解析出来
                    val data = response.body?.string()
                    runOnUiThread {
                        mBinding.textView.text =  XmlUtil.parseXMLWithPull(data)
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }
        }

        mBinding.btnXmlSax.setOnClickListener {
            thread {
                try {
                    //创建OkHttp客户端对象
                    val client = OkHttpClient()
                    //创建Request对象，用来发送HTTP请求
                    val request = Request.Builder()
                        .url("http://10.95.12.201:8080/xml/get_data.xml")
                        .build()
                    //发出网络请求，并接受回传的数据
                    val response = client.newCall(request).execute()
                    //数据解析出来
                    val data = response.body?.string()
                    runOnUiThread {
                        mBinding.textView.text =  XmlUtil.parseXMLWithSAX(data)
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }
        }

        mBinding.btnJsonJsonObject.setOnClickListener {  }

        mBinding.btnJsonGson.setOnClickListener {  }


    }

    /**数据资源初始*/
    private fun init() {
        requestArray = resources.getStringArray(R.array.arr_send_request)
    }


}
