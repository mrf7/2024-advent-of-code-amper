import com.github.ajalt.mordant.rendering.TextColors
import io.kotest.assertions.any
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay

enum class Guard(val char: Char, val dir: Direction) {
    Up('^', Direction.Up), Down('v', Direction.Down), Left('<', Direction.Left), Right('>', Direction.Right);

    fun turnRight(): Guard = when (this) {
        Up -> Right
        Down -> Left
        Left -> Up
        Right -> Down
    }

    companion object {
        fun fromChar(char: Char) =
            entries.find { it.char == char } ?: error("Non guard char $char")
    }
}

suspend fun main() {
    fun guardStart(input: List<String>) =
        input.twoDSequence().find { (_, char) -> char in Guard.entries.map { it.char } } ?: error("Guard not found")

    val obst = '#'
    suspend fun guardPath1(
        map: List<List<Char>>,
        currentCoord: Coord,
        guard: Guard,
        visit: suspend (Coord, Guard, turn: Boolean) -> Unit = { _, _, _ -> }
    ): List<Coord> {
        val aheadCoord = currentCoord.move(guard.dir)
        val aheadChar = map[aheadCoord] ?: return listOf(currentCoord)
        return when (aheadChar) {
            obst -> {
                visit(currentCoord, guard.turnRight(), true)
                return guardPath1(map, currentCoord, guard.turnRight(), visit)
            }

            else -> {
                visit(currentCoord, guard, false)
                listOf(currentCoord) + guardPath1(map, aheadCoord, guard, visit)
            }
        }
    }

    suspend fun part1(input: List<String>): Int {
        val (startCoord, guardChar) = guardStart(input)
        val startGuard = Guard.fromChar(guardChar)

        val map = input.map { it.toList() }
        val mutableMap = input.map {
            it.split("").toMutableList()
        }.toMutableList()
        val path = guardPath1(map, startCoord, startGuard) { coord, guard, turn ->
            mutableMap[coord] = when {
//                coord == startCoord -> guardChar.toString()
                turn -> "+"
                mutableMap[coord]?.any { it in listOf('-', '|', '+') } == true -> "+"
                guard in listOf(Guard.Up, Guard.Down) -> "|"
                guard in listOf(Guard.Left, Guard.Right) -> "-"
                else -> error("Oops $coord, $guard")
            }.color(if (coord == startCoord) TextColors.green else TextColors.red)
            println(mutableMap.joinToString(separator = "\n") { it.joinToString("") })
            println()
            delay(500)
        }
//        println(mutableMap.joinToString(separator = "\n") { it.joinToString("") })
        return path.toSet().size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#..
    """.trimIndent()
    part1(testInput.lines()) shouldBe 41
    part2(testInput.lines()) shouldBe 6

    val input = readInput("Day06")
    part1(input).alsoPrint()
    part2(input).alsoPrint()
}
