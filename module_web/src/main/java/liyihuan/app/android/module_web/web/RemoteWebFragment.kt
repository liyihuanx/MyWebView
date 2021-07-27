package liyihuan.app.android.module_web.web

import android.os.Bundle
import liyihuan.app.android.module_web.R
import liyihuan.app.android.module_web.utils.WebConstants
import liyihuan.app.android.module_web.web.base.BaseWebViewFragment
import java.util.*

/**
 * @ClassName: AccountWebFragment
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/7/21 22:29
 */
class RemoteWebFragment : BaseWebViewFragment() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_common_webview
    }

    companion object {
        fun newInstance(
            url: String?,
            headers: HashMap<String?, String?>?
        ): RemoteWebFragment {
            val args = Bundle()
            args.putString(WebConstants.INTENT_TAG_URL, url)
            args.putSerializable(ACCOUNT_INFO_HEADERS, headers)
            val fragment = RemoteWebFragment()
            fragment.arguments = args
            return fragment
        }
    }

}