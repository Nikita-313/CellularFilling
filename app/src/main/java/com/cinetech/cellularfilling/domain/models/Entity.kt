package com.cinetech.cellularfilling.domain.models

sealed class Entity(open val id:Int) {
    class DeadCellular(override val id:Int) : Entity(id)
    class LiveCellular(override val id:Int): Entity(id)
    class Life(override val id:Int): Entity(id)
    class DeadLife(override val id:Int): Entity(id)
}