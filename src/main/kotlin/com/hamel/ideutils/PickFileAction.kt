package com.hamel.ideutils

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger

const val PICKER_ENV = "IDEUTILS_PICKER";

class PickFileAction : AnAction() {
    private val log = Logger.getInstance(PickFileAction::class.java)

    override fun getActionUpdateThread() = ActionUpdateThread.BGT

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val picker = (System.getenv(PICKER_ENV) ?: IdeUtilSettings.instance.pickerPath).trim()
        if (picker.isEmpty()) {

            NotificationGroupManager.getInstance().getNotificationGroup("IDE Utils").createNotification(
                "No picker command configured",
                "Set a picker command in Settings › Tools › IDE Utils, or set the " + PICKER_ENV +
                        " environment variable.",
                NotificationType.WARNING,
            ).notify(project)
            return
        }
        val dir = Picks.searchDir(e, project)
        // Blocking on the picker must not happen on the EDT.
        ApplicationManager.getApplication().executeOnPooledThread {
            val path = runPicker(picker, dir) ?: return@executeOnPooledThread
            Picks.openInEditor(project, path)
        }
    }

    private fun runPicker(picker: String, dir: String): String? {
        return try {
            val proc = ProcessBuilder(picker, dir).start()
            val out = proc.inputStream.bufferedReader().readText().trim()
            proc.waitFor()
            out.ifEmpty { null }
        } catch (ex: Exception) {
            log.warn("picker failed ($picker $dir)", ex)
            null
        }
    }
}
