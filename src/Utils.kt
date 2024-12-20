import com.github.ajalt.mordant.rendering.TextColors
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */

inline fun <T> T.alsoPrint(message: T.() -> String = { toString() }) = also { println(message(this)) }


fun String.splitSpace() = this.split("\\s+".toRegex())

fun String.splitInts(regex: Regex = "\\s+".toRegex()) = this.split(regex).map { it.toInt() }
fun String.splitInts(regex: String) = this.split(regex).map { it.toInt() }

enum class Direction(val traverseX: Int, val traverseY: Int) {
    UpLeft(-1, -1), Up(0, -1), UpRight(1, -1),
    Left(-1, 0), /*         Origin          */ Right(1, 0),
    DownLeft(-1, 1), Down(0, 1), DownRight(1, 1)

}

fun Coord.move(direction: Direction, steps: Int = 1) =
    (first + direction.traverseX * steps) to (second + direction.traverseY * steps)

fun List<String>.twoDSequence() = sequence {
    forEachIndexed { y, line ->
        line.forEachIndexed { x, char ->
            yield(Coord(x, y) to char)
        }
    }
}

operator fun <T> List<List<T>>.get(x: Int, y: Int): T? {
    return getOrNull(y)?.getOrNull(x)
}

operator fun <T> List<List<T>>.get(coord: Coord): T? {
    return getOrNull(coord.second)?.getOrNull(coord.first)
}

operator fun <T> MutableList<MutableList<T>>.set(x: Int, y: Int, new: T) {
    getOrNull(y)?.set(x, new)
}

operator fun <T> MutableList<MutableList<T>>.set(coord: Coord, new: T) {
    getOrNull(coord.second)?.set(coord.first, new)
}

fun String.color(colors: TextColors) =
    colors(this)