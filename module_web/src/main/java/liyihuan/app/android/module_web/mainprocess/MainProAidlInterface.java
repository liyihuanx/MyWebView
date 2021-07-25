package liyihuan.app.android.module_web.mainprocess;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;

import com.google.gson.Gson;


import org.jetbrains.annotations.NotNull;

import java.util.Map;

import liyihuan.app.android.module_web.IWebAidlCallback;
import liyihuan.app.android.module_web.IWebAidlInterface;
import liyihuan.app.android.module_web.command.CommandsManager;
import liyihuan.app.android.module_web.command.ResultBack;

public class MainProAidlInterface extends IWebAidlInterface.Stub {

    private Context context;

    public MainProAidlInterface(Context context) {
        this.context = context;
    }

    @Override
    public void handleWebAction(int level, String actionName, String jsonParams, IWebAidlCallback callback) throws RemoteException {
        int pid = android.os.Process.myPid();
        Log.d("QWER", String.format("MainProAidlInterface: 进程ID（%d）， WebView请求（%s）, 参数 （%s）", pid, actionName, jsonParams));
        try {
            handleRemoteAction(level, actionName, new Gson().fromJson(jsonParams, Map.class), callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleRemoteAction(int level, final String cmd, Map paramMap, final IWebAidlCallback callback) throws Exception {
        CommandsManager.Companion.getInstance().executeCommand(context, level, cmd, paramMap, new ResultBack() {
            @Override
            public void onResult(int status, String cmdName, Object params) {
                try {
                    if (callback != null) {
                        callback.onResult(status, cmdName, new Gson().toJson(params));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
