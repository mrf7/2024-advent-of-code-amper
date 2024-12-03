#set( $Code = "bar" )
fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    part1(listOf("...")) shouldBe 1

    val input = readInput("Day$Day")
    part1(input).alsoPrint()
    part2(input).alsoPrint()
}
