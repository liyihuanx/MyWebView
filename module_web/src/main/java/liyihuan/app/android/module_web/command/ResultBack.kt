package liyihuan.app.android.module_web.command

/**
 * @ClassName: ResultBack
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/7/19 21:29
 */
interface ResultBack {

    fun onResult(status: Int, cmdName: String, params: Any)

}