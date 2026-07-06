package com.hamel.ideutils

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import java.io.File


internal object Picks {
    private val log = Logger.getInstance(Picks::class.java)

    fun searchDir(e: AnActionEvent, project: Project): String {/*Just search from root.*/
//        val current = e.getData(CommonDataKeys.VIRTUAL_FILE)
//        val fromFile = current?.let { if (it.isDirectory) it.path else it.parent?.path }
//        return fromFile ?: project.basePath ?: System.getProperty("user.home")
        return project.basePath ?: System.getProperty("user.home")
    }

    fun openInEditor(project: Project, path: String) {
        val trimmed = path.trim()
        val file = File(trimmed)
        if (trimmed.isEmpty() || !file.isFile) {
            if (trimmed.isNotEmpty()) log.warn("picker returned a non-file: $trimmed")
            return
        }
        ApplicationManager.getApplication().invokeLater {
            if (project.isDisposed) return@invokeLater
            val vf = LocalFileSystem.getInstance().refreshAndFindFileByPath(file.absolutePath) ?: return@invokeLater
            OpenFileDescriptor(project, vf).navigate(true)
        }
    }
}
