package liyihuan.app.android.module_web.web.webset

import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.google.gson.Gson
import liyihuan.app.android.module_web.command.Command
import liyihuan.app.android.module_web.command.CommandName
import liyihuan.app.android.module_web.utils.WebConstants
import liyihuan.app.android.module_web.web.webview.BaseWebView
import java.util.*

/**
 * @ClassName: DefaultWebChromeClient
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/7/12 23:36
 */
class DefaultWebChromeClient : WebChromeClient() {


    override fun onReceivedTitle(view: WebView, title: String?) {
        super.onReceivedTitle(view, title)
        title?.let {
            val params = HashMap<String, String>()
            params[CommandName.COMMAND_WEB_TITLE_PARAMS] = title
            (view as BaseWebView).webViewCallBack?.exec(
                view.context,
                CommandName.COMMAND_WEB_TITLE,
                Gson().toJson(params),
                view
            )
        }

    }

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)

    }

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        Log.d("QWER", "onConsoleMessage: ${consoleMessage?.message()}")
        return true
    }


    override fun onJsAlert(
        view: WebView?,
        url: String?,
        message: String?,
        result: JsResult?
    ): Boolean {
        return super.onJsAlert(view, url, message, result)
    }
}