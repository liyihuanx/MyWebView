package liyihuan.app.android.module_web.command

import android.content.Context
import android.os.IBinder
import android.util.Log
import android.webkit.WebView
import com.google.gson.Gson
import liyihuan.app.android.module_web.IWebAidlCallback
import liyihuan.app.android.module_web.IWebAidlInterface
import liyihuan.app.android.module_web.mainprocess.RemoteWebBinderPool
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
    fun executeCommand(
        context: Context,
        commandLevel: Int,
        cmd: String,
        params: String?,
        webView: WebView
    ) {
        val fromJson = Gson().fromJson(params, Map::class.java)





        if (SystemInfoUtil.inMainProcess(context, android.os.Process.myPid())) {
            Log.d("QWER", "在同一个进程: ")
            CommandsManager.instance.executeCommand(
                context,
                commandLevel,
                cmd,
                fromJson,
                object : ResultBack {
                    override fun onResult(status: Int, cmdName: String, params: Any) {
                        Log.d("QWER", "onResult: status:$status,action:$cmdName,result$params")
                        (webView as BaseWebView).handleCallBack(
                            status,
                            cmdName,
                            Gson().toJson(params)
                        )
                    }
                })
        } else {
            Log.d("QWER", "不是在同一个进程: ")
            if (commandLevel == WebConstants.LEVEL_LOCAL) {

            } else {
                webAidlInterface?.handleWebAction(
                    commandLevel,
                    cmd,
                    params,
                    object : IWebAidlCallback.Stub() {
                        override fun onResult(
                            responseCode: Int,
                            actionName: String,
                            response: String?
                        ) {
                            Log.d("QWER", "AIDL - 回调 ")
                            (webView as BaseWebView).handleCallBack(
                                responseCode,
                                actionName,
                                Gson().toJson(params)
                            )
                        }
                    })
            }
        }
    }


}