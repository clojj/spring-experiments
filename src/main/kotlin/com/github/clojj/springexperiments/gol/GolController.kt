package com.github.clojj.springexperiments.gol

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/gol")
class GolController(val golProperties: GolProperties) {

    val dummyWorld = World("dummy", setOf(Cell(1, 1), Cell(2, 1), Cell(3, 1)))

    @GetMapping("/world")
    fun world(@RequestParam("n") n: Int): List<Cell> {
        return generate(dummyWorld, n).cells.toList().sorted()
    }

}

@ConstructorBinding
@ConfigurationProperties(prefix = "gol")
data class GolProperties(val worldName: String = "")

private val neighbors = { (x, y): Cell ->
    listOf(Cell(x, y + 1), Cell(x, y - 1), Cell(x + 1, y), Cell(x - 1, y), Cell(x + 1, y + 1), Cell(x + 1, y - 1), Cell(x - 1, y + 1), Cell(x - 1, y - 1))
}

private val neighborCounts = { world: World ->
    world.cells.flatMap { c: Cell -> neighbors(c) }.sorted().groupBy { it }.mapValues { (_, value) -> value.count() }
}

private fun nextWorld(world: World): World {
    val possibleCells = neighborCounts(world)
    val nextCells = nextCells(possibleCells, world)
    return World(world.name, nextCells)
}

private fun nextCells(possibleCells: Map<Cell, Int>, world: World) =
        possibleCells.filter { (cell, count) -> count == 3 || (count == 2 && world.cells.contains(cell)) }.keys

tailrec fun generate(world: World, n: Int): World {
    if (n == 0)
        return world
    else {
        return generate(nextWorld(world), n - 1)
    }
}
