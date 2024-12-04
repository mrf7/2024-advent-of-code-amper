import io.kotest.matchers.shouldBe

typealias Coord = Pair<Int, Int>

fun main() {
    fun stringInDirection(matrix: List<String>, origin: Coord, direction: Direction, length: Int = 4): String {
        return (0..<length).map {
            origin.move(direction, it)
        }.mapNotNull { (x, y) ->
            matrix.getOrNull(y)?.getOrNull(x)
        }.joinToString(separator = "")
    }

    fun part1(input: List<String>): Int {
        return input.twoDSequence().filter { it.second == 'X' }.sumOf { (coord, _) ->
            val (x, y) = coord
            Direction.entries.count {
                "XMAS" == stringInDirection(input, Coord(x, y), it)
            }
        }
    }

    fun String.equalsAnyOrder(other: String) = this == other || this == other.reversed()
    fun part2(input: List<String>): Int {
        return input.twoDSequence().filter { it.second == 'A' }.count { (coord, _) ->
            val leftCross = stringInDirection(input, coord.move(Direction.UpLeft), Direction.DownRight, 3)
            val rightCross = stringInDirection(input, coord.move(Direction.UpRight), Direction.DownLeft, 3)
            leftCross.equalsAnyOrder("MAS") && rightCross.equalsAnyOrder("MAS")
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
