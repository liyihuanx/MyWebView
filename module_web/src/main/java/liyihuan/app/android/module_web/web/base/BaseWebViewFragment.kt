package liyihuan.app.android.module_web.web.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import liyihuan.app.android.module_web.R
import liyihuan.app.android.module_web.command.CommandDispatcher
import liyihuan.app.android.module_web.utils.WebConstants
import liyihuan.app.android.module_web.web.callback.WebViewCallBack
import liyihuan.app.android.module_web.web.webview.BaseWebView
import java.util.*

/**
 * @ClassName: BaseWebViewFragment
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/7/15 22:40
 */
abstract class BaseWebViewFragment : Fragment(), WebViewCallBack {

    private lateinit var accountInfoHeaders: HashMap<String, String>


    private lateinit var rootview: View
    private lateinit var webView: BaseWebView
    lateinit var webUrl: String

    companion object {
        val ACCOUNT_INFO_HEADERS = "account_header"
    }

    fun setTitle(titleId: Int) {
        requireActivity().setTitle(titleId)
    }

    fun setTitle(title: CharSequence?) {
        requireActivity().title = title
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView.webViewCallBack = this
        CommandDispatcher.instance.initAidlConnected(requireContext())
        loadUrl()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview = inflater.inflate(getLayoutRes(), container, false)
        webView = rootview.findViewById(R.id.web_view)
        return rootview
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            webUrl = it.getString(WebConstants.INTENT_TAG_URL).toString()
            if (it.containsKey(ACCOUNT_INFO_HEADERS)) {
//                accountInfoHeaders = it.getSerializable(ACCOUNT_INFO_HEADERS) as HashMap<String, String>
            }
        }
    }

    abstract fun getLayoutRes(): Int

    private fun loadUrl() {
        webView.loadUrl(webUrl)
    }

    /********************************* 打开webView的回调方法 ********************************************/

    override fun pageStarted(url: String?) {

    }

    override fun pageFinished(url: String?) {

    }

    override fun overrideUrlLoading(view: WebView?, url: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onError() {

    }

    override fun exec(
        context: Context,
        cmd: String,
        params: String?,
        webView: WebView
    ) {
        CommandDispatcher.instance.executeCommand(context, cmd, params, webView)
    }

}