package liyihuan.app.android.module_web.web.javascriptinterface

import android.content.Context
import android.os.Handler
import android.webkit.JavascriptInterface

/**
 * @ClassName: WebViewJavascriptInterface
 * @Description: H5调用Android方法
 * @Author: liyihuan
 * @Date: 2021/7/15 22:24
 */
class WebViewJavascriptInterface(context: Context) {

    private val mContext by lazy { context }
    private val mHandler by lazy { Handler() }

    var javascriptCommand: ((context: Context, cmd: String, param: String?) -> Unit)? = null


    /**
     * 统一暴露给H5使用的方法
     * H5调用时只需要传 指令cmd 和 param 参数
     */
    @JavascriptInterface
    fun post(cmd: String, param: String?) {
        mHandler.post {
            try {
                if (javascriptCommand != null) {
                    javascriptCommand?.invoke(mContext, cmd, param)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}