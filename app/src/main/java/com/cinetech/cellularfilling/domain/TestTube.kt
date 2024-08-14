package com.cinetech.cellularfilling.domain

import com.cinetech.cellularfilling.domain.models.Entity
import kotlin.random.Random

class TestTube {

    private var entityCounter = 0
    private var createLifeCounter = 0
    private var killLifeCounter = 0
    private val tube = mutableListOf<Entity>()
    private var tubeListener: (List<Entity>) -> Unit = {}

    fun addRandomEntityToTube() {

        if (Random.nextBoolean()) {
            tube.add(Entity.DeadCellular(entityCounter))
            killLifeCounter++
            createLifeCounter = 0
        } else {
            tube.add(Entity.LiveCellular(entityCounter))
            createLifeCounter++
            killLifeCounter = 0
        }
        entityCounter++

        if (checkCanLifeAppear()) addLife()
        if (checkLifeMayDie()) killLastLife()

        notifyListeners()
    }

    fun setTubeListener(callback: (List<Entity>) -> Unit) {
        tubeListener = callback
    }

    fun removeTubeListener() {
        tubeListener = {}
    }

    private fun notifyListeners() {
        tubeListener.invoke(tube.toList())
    }

    private fun checkCanLifeAppear(): Boolean {
        return createLifeCounter == 3
    }

    private fun checkLifeMayDie(): Boolean {
        return killLifeCounter == 3
    }

    private fun addLife() {
        tube.add(Entity.Life(entityCounter))
        entityCounter++
        createLifeCounter = 0
    }

    private fun killLastLife() {
        val lastLifeIndex = tube.indexOfLast { it is Entity.Life }
        if (lastLifeIndex != -1) {
            tube[lastLifeIndex] = Entity.DeadLife(entityCounter)
            entityCounter++
            killLifeCounter = 0
        }
    }
}