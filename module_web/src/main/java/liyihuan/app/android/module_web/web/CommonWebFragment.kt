package liyihuan.app.android.module_web.web

import android.os.Bundle
import liyihuan.app.android.module_web.R
import liyihuan.app.android.module_web.utils.WebConstants
import liyihuan.app.android.module_web.web.base.BaseWebViewFragment

/**
 * @ClassName: CommonWebFragment
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/7/19 22:14
 */
class CommonWebFragment : BaseWebViewFragment() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_common_webview
    }

    override fun getCommandLevel(): Int {
        return WebConstants.LEVEL_BASE
    }

    companion object {
        fun newInstance(url: String): CommonWebFragment {
            val args = Bundle()
            args.putSerializable(WebConstants.INTENT_TAG_URL, url)
            val fragment = CommonWebFragment()
            fragment.arguments = args
            return fragment
        }
    }
}