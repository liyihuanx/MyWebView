package liyihuan.app.android.module_web;

// Declare any non-default types here with import statements
import liyihuan.app.android.module_web.IWebAidlCallback;

interface IWebAidlInterface {
     /**
      * actionName: 不同的action， jsonParams: 需要根据不同的action从map中读取并依次转成其他
      */
      void handleWebAction(int level, String actionName, String jsonParams, in IWebAidlCallback callback);
}
