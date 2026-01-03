import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Long {
        val ranges = buildList {
            val rawRanges = input[0].split(',')
            rawRanges.forEach { range ->
                val (begin, end) = range.split("-")
                add(begin.toLong() to end.toLong())
            }
        }

        var ans: Long = 0

        ranges.forEach { range ->
            for (i in range.first..range.second) {
                if (i.digitCount % 2 != 0) continue
                if (i.isInvalidId1()) ans += i
            }
        }

        return ans
    }

    fun part2(input: List<String>): Long {
        val ranges = buildList {
            val rawRanges = input[0].split(',')
            rawRanges.forEach { range ->
                val (begin, end) = range.split("-")
                add(begin.toLong() to end.toLong())
            }
        }

        var ans: Long = 0

        ranges.forEach { range ->
            for (i in range.first..range.second) {
                if (i.isInvalidId2()) ans += i
            }
        }

        return ans
    }


    // Or read a large test input from the `src/input/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
//    println("Test Part1 Day01: ${part1(testInput)}")
    println("Test Part2 Day02: ${part2(testInput)}")

    // Read the input from the `src/input/Day02.txt` file.
    val input = readInput("Day02")
//    println("Part1 Day01: ${part1(input)}")
    println("Part2 Day02: ${part2(input)}")
}

private val Long.digitCount: Int
    get() {
        var n = abs(this)
        var ans = 1
        while (n >= 10) {
            ++ans
            n /= 10
        }
        return ans
    }

private fun Long.isInvalidId1(): Boolean {
    var left = this
    var right: Long = 0
    var multiplier = 1
    for (i in 0..<digitCount / 2) {
        right += multiplier * (left % 10)
        multiplier *= 10
        left /= 10
    }

    return left == right
}

private fun Long.divisorsOfDigitCount(): List<Int> = buildList {
    val digitCount = digitCount
    add(1)
    for (i in 2..digitCount / 2) {
        if (digitCount % i == 0) add(i)
    }
}

private fun Long.isInvalidId2(): Boolean {
    val digitCount = digitCount
    if (digitCount == 1) return false

    val divisorsOfDigitCount = divisorsOfDigitCount()
    divisorsOfDigitCount.forEach { divisor ->
        var rest = this
        val parts = buildSet {
            for (i in 0..<digitCount / divisor) {
                var part: Long = 0
                var multiplier = 1
                for (j in 0..<divisor) {
                    part += multiplier * (rest % 10)
                    multiplier *= 10
                    rest /= 10
                }
                add(part)
            }
        }

        if (parts.size == 1) return true
    }

    return false
}
