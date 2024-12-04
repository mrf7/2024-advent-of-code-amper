import io.kotest.matchers.shouldBe
import org.jetbrains.kotlinx.multik.api.Multik
import org.jetbrains.kotlinx.multik.api.toNDArray
import org.jetbrains.kotlinx.multik.ndarray.data.get

typealias Coord = Pair<Int, Int>

enum class Direction(val traverseX: Int, val traverseY: Int) {
    UpLeft(-1, -1), Up(0, -1), UpRight(1, -1),
    Left(-1, 0), /*         Origin          */ Right(1, 0),
    DownLeft(-1, 1), Down(0, 1), DownRight(1, 1)

}

fun main() {
    fun stringInDirection(matrix: List<String>, origin: Coord, direction: Direction, length: Int = 4): String {
        val (originX, originY) = origin
        return (0..<length).map {
            (originX + direction.traverseX * it) to (originY + direction.traverseY * it)
        }.mapNotNull { (x, y) ->
            matrix.getOrNull(y)?.getOrNull(x)
        }.joinToString(separator = "")
    }

    fun part1(input: List<String>): Int {
        return input.withIndex().sumOf { (y, line) ->
            line.withIndex().filter { it.value == 'X' }.sumOf { (x, char) ->
                Direction.entries.count {
                    "XMAS" == stringInDirection(input, Coord(x, y), it)
                }
            }
        }
    }

    fun String.equalsAnyOrder(other: String) = this == other || this == other.reversed()
    fun part2(input: List<String>): Int {
        return input.withIndex().sumOf { (y, line) ->
            line.withIndex().filter { it.value == 'A' }.count { (x, char) ->
                val leftCross = stringInDirection(input, Coord(x - 1, y - 1), Direction.DownRight, 3)
                val rightCross = stringInDirection(input, Coord(x + 1, y - 1), Direction.DownLeft, 3)
                leftCross.equalsAnyOrder("MAS") && rightCross.equalsAnyOrder("MAS")
            }
        }
    }

    val testInput = """
        MMMSXXMASM
        MSAMXMSMSA
        AMXSXMAAMM
        MSAMASMSMX
        XMASAMXAMM
        XXAMMXXAMA
        SMSMSASXSS
        SAXAMASAAA
        MAMMMXMMMM
        MXMXAXMASX
    """.trimIndent()
    part1(testInput.lines()) shouldBe 18
    part2(testInput.lines()) shouldBe 9

    val input = readInput("Day04")
    part1(input).alsoPrint()
    part2(input).alsoPrint()
}
