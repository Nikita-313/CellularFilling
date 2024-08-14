package com.cinetech.cellularfilling.ui

import androidx.lifecycle.ViewModel
import com.cinetech.cellularfilling.domain.TestTube
import com.cinetech.cellularfilling.domain.models.Entity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainActivityViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<List<Entity>>(emptyList())
    val uiState: StateFlow<List<Entity>> = _uiState

    private val testTube = TestTube()

    init {
        testTube.setTubeListener {
            tubeListener(it)
        }
    }

    private fun tubeListener(entities: List<Entity>) {
        _uiState.value = entities
    }

    fun addEntityToTube() {
        testTube.addRandomEntityToTube()
    }

}