import kotlin.math.absoluteValue

fun main() {
    fun splitLists(input: List<String>) = input.map {
        val (first, second) = it.splitInts()
        first to second
    }.unzip()

    fun part1(input: List<String>): Int {
        val (first, second) = splitLists(input)
        return (first.sorted()).zip(second.sorted()).sumOf { (first, second) ->
            (second - first).absoluteValue
        }
    }

    fun part2(input: List<String>): Int {
        val (first, second) = splitLists(input)
        return first.sumOf { firstNum ->
            firstNum * second.count { it == firstNum }
        }
    }

    val testInput = """
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3
    """.trimIndent().lines()
    // Test if implementation meets criteria from the description, like:
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Or read a large test input from the `src/Day01_test.txt` file:
//    val testInput = readInput("Day01_test")
    part1(testInput).alsoPrint()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).alsoPrint()
    part2(input).alsoPrint()
}
