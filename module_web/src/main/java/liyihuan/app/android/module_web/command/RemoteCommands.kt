package liyihuan.app.android.module_web.command

import liyihuan.app.android.module_web.utils.WebConstants

/**
 * @ClassName: AccountLevelCommands
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/7/25 21:17
 */
class RemoteCommands : BaseCommand() {
    override fun getCommandLevel(): Int {
        return WebConstants.LEVEL_REMOTE
    }

}