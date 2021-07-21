package liyihuan.app.android.module_web.command

import java.util.*
import kotlin.collections.HashMap

/**
 * @ClassName: BaseCommand
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/7/19 21:28
 */
abstract class BaseCommand {
    // <指令名字cmd，指令Command>
    var commands: HashMap<String, Command> = HashMap()

    abstract fun getCommandLevel(): Int

    fun registerCommand(command: Command) {
        commands[command.cmdName()] = command
    }
}