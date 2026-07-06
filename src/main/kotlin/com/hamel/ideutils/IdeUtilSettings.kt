package com.hamel.ideutils

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@Service(Service.Level.APP)
@State(name = "IdeUtilSettings", storages = [Storage("ideutils.xml")])
class IdeUtilSettings : PersistentStateComponent<IdeUtilSettings.State> {

    data class State(var pickerPath: String = "")

    private var myState = State()

    override fun getState(): State = myState
    override fun loadState(state: State) {
        myState = state
    }

    var pickerPath: String
        get() = myState.pickerPath
        set(value) {
            myState.pickerPath = value
        }

    companion object {
        val instance: IdeUtilSettings
            get() = ApplicationManager.getApplication().getService(IdeUtilSettings::class.java)
    }
}
