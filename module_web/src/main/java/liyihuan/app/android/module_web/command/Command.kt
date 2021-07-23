package liyihuan.app.android.module_web.command

import android.content.Context

interface Command {
    fun cmdName(): String
    fun exec(
        context: Context,
        params: Map<*, *>?,
        resultBack: ResultBack?
    )

}