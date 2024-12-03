import io.kotest.matchers.shouldBe

fun main() {
    fun part1(input: List<String>): Int {
        val thing = input.joinToString(separator = "")
        return """mul\((\d+),(\d+)\)""".toRegex().findAll(thing)
            .map { it.groupValues[1].toInt() * it.groupValues[2].toInt() }
            .sum()
    }

    fun part2(input: List<String>): Int {
        val thing = input.joinToString(separator = "")
        val matches =
            """(mul\(\d+,\d+\)|do\(\)|don't\(\))""".toRegex()
                .findAll(thing)
                .toList()
                .map { it.groupValues[1] }
        var doMull = true
        println("in here ${matches.size}")
        return matches.mapNotNull { matchResult: String ->
            when (matchResult.takeWhile { it != '(' }) {
                "do" -> {
                    doMull = true
                    null
                }

                "don't" -> {
                    doMull = false
                    null
                }

                "mul" -> {
                    if (doMull) {
                        val nums = """mul\((\d+),(\d+)\)""".toRegex().find(matchResult)
                            ?.groupValues
                            ?.drop(1)
                            ?.map { it.toInt() }
                            ?: error("no match in $matchResult")
                        nums.alsoPrint()
                        nums[0] * nums[1]
                    } else {
                        null
                    }
                }

                else -> error("wrong match $matchResult")
            }
        }.sum()
    }

    val testIn = """xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"""
    part2(listOf(testIn)) shouldBe 48

    val input = readInput("Day03")
    part1(input).alsoPrint()
    part2(input).alsoPrint()
}
