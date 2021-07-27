package liyihuan.app.android.module_web.command

import android.content.Context
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

    private val remoteCommands by lazy {
        RemoteCommands()
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
            WebConstants.LEVEL_REMOTE -> remoteCommands.registerCommand(command)
        }
    }

    fun executeCommand(
        context: Context,
        commandLevel: Int,
        cmd: String,
        params: Map<*, *>?,
        resultBack: ResultBack?
    ) {
        when(commandLevel){
            WebConstants.LEVEL_LOCAL -> localCommand.commands[cmd]?.exec(context, params, resultBack)
            WebConstants.LEVEL_REMOTE -> remoteCommands.commands[cmd]?.exec(context, params, resultBack)
        }
    }

    fun isLocalCommand(cmd: String): Boolean {
        val command = localCommand.commands[cmd]
        return command != null
    }

}