package liyihuan.app.android.module_web.command

import android.content.Context
import android.util.Log
import android.widget.Toast
import liyihuan.app.android.module_web.utils.WebConstants

/**
 * @ClassName: LocalCommand
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/7/19 23:42
 */
class LocalCommand : BaseCommand() {

    override fun getCommandLevel(): Int {
        return WebConstants.LEVEL_LOCAL
    }

    private val webTitleCommand by lazy {
        object : Command {
            override fun cmdName(): String {
                return CommandName.COMMAND_WEB_TITLE;
            }

            override fun exec(context: Context, params: Map<*, *>?, resultBack: ResultBack?) {
//                resultBack?.onResult(WebConstants.SUCCESS, this.cmdName(), params)
            }
        }
    }

//    private val showToastCommand by lazy {
//        object : Command {
//            override fun cmdName(): String {
//                return CommandName.COMMAND_SHOW_TOAST
//            }
//
//            override fun exec(context: Context, params: Map<*, *>?, resultBack: ResultBack?) {
//                Toast.makeText(context, params?.get(CommandName.COMMAND_MESSAGE).toString(), Toast.LENGTH_LONG).show()
//            }
//        }
//    }

    init {
        registerCommand(webTitleCommand)
//        registerCommand(showToastCommand)
    }
}