package liyihuan.app.android.module_web.web.callback

import android.content.Context
import android.webkit.WebView

/**
 * @ClassName: WebViewCallBack
 * @Description: 打开WebActivity时的回调
 * @Author: liyihuan
 * @Date: 2021/7/15 22:22
 */
interface WebViewCallBack {


    fun getCommandLevel(): Int

    fun pageStarted(url: String?)

    fun pageFinished(url: String?)

    fun overrideUrlLoading(view: WebView?, url: String?): Boolean

    fun onError()

    fun exec(
        context: Context,
        commandLevel: Int,
        cmd: String,
        params: String?,
        webView: WebView
    )

}