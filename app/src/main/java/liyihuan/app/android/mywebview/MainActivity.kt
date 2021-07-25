package liyihuan.app.android.mywebview

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import liyihuan.app.android.module_web.command.Command
import liyihuan.app.android.module_web.command.CommandsManager
import liyihuan.app.android.module_web.command.ResultBack
import liyihuan.app.android.module_web.utils.AidlError
import liyihuan.app.android.module_web.utils.WebConstants
import liyihuan.app.android.module_web.web.webview.BaseWebView
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CommandsManager.instance.registerCommand(WebConstants.LEVEL_REMOTE, appDataProviderCommand)


        btnWebActivity.setOnClickListener {
            WebActivity.startWeb(
                this,
                "web",
                "https://xw.qq.com/?f=qqcom"
            )
        }

        btnMyWeb.setOnClickListener {
            WebActivity.startWeb(
                this@MainActivity,
                "AIDL测试",
                BaseWebView.CONTENT_SCHEME + "aidl.html"
            )

        }
    }


    // 获取native data
    private val appDataProviderCommand: Command = object : Command {
        override fun cmdName(): String {
            return "appDataProvider"
        }

        override fun exec(context: Context, params: Map<*, *>?, resultBack: ResultBack?) {
            try {
                var callbackName = ""
                if (params?.get("type") == null) {
                    val aidlError = AidlError(
                        WebConstants.ERRORCODE.ERROR_PARAM,
                        WebConstants.ERRORMESSAGE.ERROR_PARAM
                    )
                    resultBack?.onResult(WebConstants.FAILED, cmdName(), aidlError)
                    return
                }
                if (params[WebConstants.WEB2NATIVE_CALLBACk] != null) {
                    callbackName = params[WebConstants.WEB2NATIVE_CALLBACk].toString()
                }
                val type = params["type"].toString()
                val map = HashMap<Any?, Any?>()
                when (type) {
                    "account" -> {
                        map["accountId"] = "id123456"
                        map["accountName"] = "liyuhuanx"
                    }
                }
                if (!TextUtils.isEmpty(callbackName)) {
                    map[WebConstants.NATIVE2WEB_CALLBACK] = callbackName
                }
                resultBack?.onResult(WebConstants.SUCCESS, cmdName(), map)
            } catch (e: Exception) {
                e.printStackTrace()
                val aidlError = AidlError(
                    WebConstants.ERRORCODE.ERROR_PARAM,
                    WebConstants.ERRORMESSAGE.ERROR_PARAM
                )
                resultBack?.onResult(WebConstants.FAILED, cmdName(), aidlError)
            }
        }

    }
}