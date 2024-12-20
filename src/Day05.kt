import io.kotest.matchers.shouldBe
import java.util.Comparator

typealias Rule = Pair<Int, Int>

fun main() {
    fun isInOrder(
        edit: List<Int>,
        rulesLines: List<Pair<Int, Int>>
    ): Boolean {
        val editsIndex = edit.withIndex().associate { it.value to it.index }
        return rulesLines.all { (before, after) ->
            val beforeIndex = editsIndex[before] ?: return@all true
            val afterIndex = editsIndex[after] ?: return@all true
            beforeIndex < afterIndex
        }
    }

    fun part1(input: List<String>): Int {
        val rulesLines = input.takeWhile { it.isNotBlank() }.map {
            val nums = it.splitInts("|")
            nums.first() to nums[1]
        }
        val edits = input.takeLastWhile { it.isNotBlank() }.map { it.trim().splitInts(",") }

        return edits
            .filter { edit ->
                isInOrder(edit, rulesLines)
            }.sumOf {
                it[it.size / 2]
            }

    }

    fun rulesComaparator(rules: List<Rule>) = Comparator<Int> { a, b ->
        val rulesMap = rules.groupBy(keySelector = Rule::first, valueTransform = Rule::second)
        when {
            rulesMap[a]?.contains(b) == true -> 1
            rulesMap[b]?.contains(a) == true -> -1
            else -> 0
        }
    }

    fun part2(input: List<String>): Int {
        val rulesLines = input.takeWhile { it.isNotBlank() }.map {
            val nums = it.splitInts("|")
            nums.first() to nums[1]
        }
        val edits = input.takeLastWhile { it.isNotBlank() }.map { it.trim().splitInts(",") }
        val comp = rulesComaparator(rulesLines)
        return edits
            .filter {
                !isInOrder(it, rulesLines)
            }
            .map { edit ->
                edit.sortedWith(comp)
            }
            .sumOf { it[it.size / 2] }
    }

    val testInput = """
        47|53
        97|13
        97|61
        97|47
        75|29
        61|13
        75|53
        29|13
        97|29
        53|29
        61|53
        97|53
        61|29
        47|13
        75|47
        97|75
        47|61
        75|61
        47|29
        75|13
        53|13

        75,47,61,53,29
        97,61,53,29,13
        75,29,13
        75,97,47,61,53
        61,13,29
        97,13,75,29,47 
    """.trimIndent()
    part1(testInput.lines()) shouldBe 143
    part2(testInput.lines()) shouldBe 123
    val input = readInput("Day05")
    part1(input).alsoPrint()
    part2(input).alsoPrint()
}
