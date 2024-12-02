import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {
    fun List<Int>.isValid(): Boolean {
        var sign: Int? = null
        return windowed(2).all { (first, second) ->
            val diff = (first - second)
            sign = sign ?: diff.sign
            if (sign != diff.sign) {
                false
            } else {
                diff.absoluteValue in 1..3
            }
        }
    }

    fun part1(input: List<String>): Int {
        return input
            .map(String::splitInts)
            .count(List<Int>::isValid)
    }


    fun part2(input: List<String>): Int {
        val reports = input
            .map(String::splitInts)
        return reports.count { report ->
            if (report.isValid()) {
                true
            } else {
                (0..report.lastIndex).any { index ->
                    val updated = report.toMutableList().also { it.removeAt(index) }
                    updated.isValid()
                }
            }
        }
    }

    val testInput = """
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
    """.trimIndent().lines()
    check(part2(testInput) == 4)

    val input = readInput("Day02")
    part1(input).alsoPrint()
    part2(input).alsoPrint()
}
