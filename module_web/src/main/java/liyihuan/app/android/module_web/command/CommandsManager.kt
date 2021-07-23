package liyihuan.app.android.module_web.command

import android.content.Context
import android.util.Log
import liyihuan.app.android.module_web.utils.WebConstants

/**
 * @ClassName: CommandsManager
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/7/15 23:11
 */
class CommandsManager {

    private val localCommand by lazy {
        LocalCommand()
    }


    companion object {
        val instance: CommandsManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CommandsManager()
        }
    }


    /**
     * 动态注册command
     * 应用场景：其他模块在使用WebView的时候，需要增加特定的command，动态加进来
     */
    fun registerCommand(commandLevel: Int, command: Command) {

        when (commandLevel) {
            WebConstants.LEVEL_LOCAL -> localCommand.registerCommand(command)
//            WebConstants.LEVEL_BASE -> baseLevelCommands.registerCommand(command)
//            WebConstants.LEVEL_ACCOUNT -> accountLevelCommands.registerCommand(command)
        }
    }

    fun executeCommand(
        context: Context,
        cmd: String,
        params: Map<*, *>?,
        resultBack: ResultBack?
    ) {
        val command = localCommand.commands[cmd]
        command?.exec(context, params, resultBack)

    }

}