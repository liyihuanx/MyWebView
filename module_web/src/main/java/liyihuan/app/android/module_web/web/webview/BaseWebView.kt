package liyihuan.app.android.module_web.web.webview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.webkit.WebView
import liyihuan.app.android.module_web.web.callback.WebViewCallBack
import liyihuan.app.android.module_web.web.javascriptinterface.WebViewJavascriptInterface
import liyihuan.app.android.module_web.web.webset.DefaultWebChromeClient
import liyihuan.app.android.module_web.web.webset.DefaultWebSetting
import liyihuan.app.android.module_web.web.webset.DefaultWebViewClient

/**
 * @ClassName: BaseWebView
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/7/15 22:09
 */
class BaseWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : WebView(context, attrs, defStyleAttr) {

    companion object {
        val CONTENT_SCHEME = "file:///android_asset/"
    }

    //
    private val javascriptInterface: WebViewJavascriptInterface by lazy {
        WebViewJavascriptInterface(context)
    }

    var webViewCallBack: WebViewCallBack? = null


    init {
        DefaultWebSetting.getSetting(this)
        webViewClient = DefaultWebViewClient()
        webChromeClient = DefaultWebChromeClient()

        /**
         * Web Native交互触发
         */
        javascriptInterface.javascriptCommand = { context, cmd, param ->

            webViewCallBack?.exec(context, cmd, param, this)
        }
        addJavascriptInterface(javascriptInterface, "webview")

    }

    /**
     * 执行Js的回调
     */
    fun handleCallBack(status: Int, cmdName: String, result: Any?) {
        val trigger = "javascript:dj.callback($result)"
        Log.d("QWER", "handleCallBack: $trigger")
        evaluateJavascript(trigger, null)
    }
}