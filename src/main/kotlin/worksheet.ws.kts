import com.github.clojj.springexperiments.gol.Cell

fun triples(n: Int) =
        (1..n).flatMap { x ->
            (1..n).flatMap { y ->
                (1..n).map { z ->
                    Triple(x, y, z)
                }
            }
        }

fun pairs(cell: Cell) =
        (-1..1).flatMap { x ->
            (-1..1).map { y ->
                Pair(x, y)
            }
                    .filter { it.first != 0 || it.second != 0 }
                    .map { Cell(cell.x + it.first, cell.y + it.second) }
        }

println(pairs(Cell(3, 3)))