package liyihuan.app.android.module_web.command

import android.content.Context
import android.os.IBinder
import android.util.Log
import android.webkit.WebView
import com.google.gson.Gson
import liyihuan.app.android.module_web.IWebAidlCallback
import liyihuan.app.android.module_web.IWebAidlInterface
import liyihuan.app.android.module_web.mainprocess.RemoteWebBinderPool
import liyihuan.app.android.module_web.utils.MainLooper
import liyihuan.app.android.module_web.utils.SystemInfoUtil
import liyihuan.app.android.module_web.utils.WebConstants
import liyihuan.app.android.module_web.web.webview.BaseWebView

/**
 * @ClassName: CommandDispatcher
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/7/15 23:12
 */
class CommandDispatcher private constructor() {


    // 实现跨进程通信的接口
    private var webAidlInterface: IWebAidlInterface? = null

    companion object {
        val instance: CommandDispatcher by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CommandDispatcher()
        }
    }


    fun initAidlConnected(context: Context) {
        if (webAidlInterface != null) {
            return
        }
        Thread(Runnable {
            val binderPool: RemoteWebBinderPool = RemoteWebBinderPool.getInstance(context)
            val iBinder: IBinder = binderPool.queryBinder(RemoteWebBinderPool.BINDER_WEB_AIDL)
            webAidlInterface = IWebAidlInterface.Stub.asInterface(iBinder)

        }).start()
    }

    /**
     * 执行指令
     */
    fun executeCommand(context: Context, cmd: String, params: String?, webView: WebView) {
        if (CommandsManager.instance.isLocalCommand(cmd)) {
            val fromJson = Gson().fromJson(params, Map::class.java)
            CommandsManager.instance.executeCommand(
                context,
                WebConstants.LEVEL_LOCAL,
                cmd,
                fromJson,
                object : ResultBack {
                    override fun onResult(status: Int, cmdName: String, params: Any) {
                        (webView as BaseWebView).handleCallBack(
                            status,
                            cmdName,
                            Gson().toJson(params)
                        )
                    }
                })
        } else {
            webAidlInterface?.handleWebAction(
                WebConstants.LEVEL_REMOTE,
                cmd,
                params,
                object : IWebAidlCallback.Stub() {
                    override fun onResult(
                        responseCode: Int,
                        actionName: String,
                        response: String?
                    ) {
                        Log.d("QWER", "IWebAidlCallback - 回调 ")
                        MainLooper.runOnUiThread {
                            (webView as BaseWebView).handleCallBack(
                                responseCode,
                                actionName,
                                response
                            )
                        }
                    }
                })
        }

//        if (SystemInfoUtil.inMainProcess(context, android.os.Process.myPid())) {
//
//        } else {
//            Log.d("QWER", "不是在同一个进程: ")
//        }
    }


}