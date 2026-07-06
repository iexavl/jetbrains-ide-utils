package com.hamel.ideutils

import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.options.BoundConfigurable
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel


class IdeUtilsConfigurable : BoundConfigurable("IDE Utils") {

    private val settings = IdeUtilSettings.instance

    override fun createPanel(): DialogPanel = panel {
        row("Picker command:") {
            val descriptor = FileChooserDescriptor(
                true,
                false,
                false,
                false,
                false,
                false
            )
                .withTitle("Select Picker Executable")
            val field = TextFieldWithBrowseButton().apply {
                addBrowseFolderListener(null, descriptor)
            }
            cell(field)
                .align(AlignX.FILL)
                .bindText(settings::pickerPath)
                .comment(
                    "Executable run to pick a file; receives the search directory as its " +
                            "one argument and must print the chosen path to stdout. " +
                            "Overridden by the IDEUTILS_PICKER environment variable when set."
                )
        }
    }
}
