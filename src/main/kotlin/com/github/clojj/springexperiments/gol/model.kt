package com.github.clojj.springexperiments.gol

data class Cell(val x: Int, val y: Int) : Comparable<Cell> {
    override fun compareTo(other: Cell): Int = compareValuesBy(this, other, { it.x }, { it.y })
}

data class World(val name: String, val cells: Set<Cell>)
