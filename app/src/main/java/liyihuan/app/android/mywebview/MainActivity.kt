package liyihuan.app.android.mywebview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import liyihuan.app.android.module_web.utils.WebConstants
import liyihuan.app.android.module_web.web.webview.BaseWebView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnWebActivity.setOnClickListener {
            WebActivity.startAccountWeb(
                this,
                "web",
                "https://xw.qq.com/?f=qqcom",
                WebConstants.LEVEL_BASE
            )
        }

        btnMyWeb.setOnClickListener {
            WebActivity.startAccountWeb(
                this@MainActivity,
                "AIDL测试",
                BaseWebView.CONTENT_SCHEME + "aidl.html",
                WebConstants.LEVEL_ACCOUNT
            )

        }
    }
}