package liyihuan.app.android.module_web.command

import android.content.Context
import android.util.Log
import android.webkit.WebView
import com.google.gson.Gson
import liyihuan.app.android.module_web.IWebAidlInterface
import liyihuan.app.android.module_web.web.webview.BaseWebView

/**
 * @ClassName: CommandDispatcher
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/7/15 23:12
 */
class CommandDispatcher private constructor() {


    // 实现跨进程通信的接口
    private lateinit var webAidlInterface: IWebAidlInterface

    companion object {
        val instance: CommandDispatcher by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CommandDispatcher()
        }
    }


    fun initAidlConnected() {
        Thread(Runnable {
//            Log.i("QWER", "Begin to connect with main process")
//            val binderPool: RemoteWebBinderPool = RemoteWebBinderPool.getInstance(context)
//            val iBinder: IBinder = binderPool.queryBinder(RemoteWebBinderPool.BINDER_WEB_AIDL)
//            webAidlInterface = IWebAidlInterface.Stub.asInterface(iBinder)
//            Log.i("QWER", "Connect success with main process")
        }).start()
    }

    /**
     * 执行指令
     */
    fun executeCommand(
        context: Context,
        cmd: String,
        params: String?,
        webView: WebView
    ) {
        val fromJson = Gson().fromJson(params, Map::class.java)
        CommandsManager.instance.executeCommand(context, cmd, fromJson, object : ResultBack {
            override fun onResult(status: Int, cmdName: String, params: Any) {
                Log.d("QWER", "onResult: status:$status,action:$cmdName,result$params")
                (webView as BaseWebView).handleCallBack(status, cmdName, Gson().toJson(params))
            }
        })
    }


}