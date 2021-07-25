package liyihuan.app.android.mywebview

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import liyihuan.app.android.module_web.utils.WebConstants
import liyihuan.app.android.module_web.web.RemoteWebFragment
import liyihuan.app.android.module_web.web.CommonWebFragment
import liyihuan.app.android.module_web.web.base.BaseWebViewFragment
import java.util.*

/**
 * @ClassName: WebActivity
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/7/19 22:07
 */
class WebActivity : AppCompatActivity() {


    private var title: String? = null
    private var url: String? = null

    lateinit var wbFragment: BaseWebViewFragment


    companion object {
        fun startWeb(
            context: Context,
            title: String?,
            url: String?,
            headers: HashMap<String, String>? = null
        ) {
            val intent = Intent(context, WebActivity::class.java)
            val bundle = Bundle()
            bundle.putString(WebConstants.INTENT_TAG_TITLE, title)
            bundle.putString(WebConstants.INTENT_TAG_URL, url)
            if (headers != null) {
                bundle.putSerializable(WebConstants.INTENT_TAG_HEADERS, headers)
            }
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_web)
        title = intent.getStringExtra(WebConstants.INTENT_TAG_TITLE)
        url = intent.getStringExtra(WebConstants.INTENT_TAG_URL)
        setTitle(title)

        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()

        wbFragment = RemoteWebFragment.newInstance(
            url,
            intent.extras?.getSerializable(WebConstants.INTENT_TAG_HEADERS) as HashMap<String?, String?>?
        )
        transaction.replace(R.id.web_view_fragment, wbFragment).commitAllowingStateLoss()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
    }
}
