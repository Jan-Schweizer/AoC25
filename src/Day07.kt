import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        var start = input[0].indexOfFirst { it == 'S' }
        var end = start
        val beamIndices: MutableSet<Int> = mutableSetOf(start)
        var ans = 0
        for (i in 2..<input.size step 2) {
            val line = input[i]
            for (j in start..end) {
                if (line[j] == '^' && j in beamIndices) {
                    start = min(start, j - 1)
                    end = max(end, j + 1)
                    beamIndices.remove(j)
                    beamIndices.add(j - 1)
                    beamIndices.add(j + 1)
                    ++ans
                }
            }
        }

        return ans
    }

    fun part2(input: List<String>): Long {
        val dpTable: MutableMap<Pair<Int, Int>, Long> = mutableMapOf()
        val start = input[0].indexOfFirst { it == 'S' }
        val lineNo = 2
        val beamIndex = start

        findPath(input, lineNo, beamIndex, dpTable)

        return dpTable[0 to start] ?: -1
    }

    // Or read a large test input from the `src/input/Day07_test.txt` file:
    val testInput = readInput("Day07_test")
//    println("Test Part1 Day07: ${part1(testInput)}")
    println("Test Part2 Day07: ${part2(testInput)}")

    // Read the input from the `src/input/Day07.txt` file.
    val input = readInput("Day07")
//    println("Part1 Day07: ${part1(input)}")
    println("Part2 Day07: ${part2(input)}")
}

private fun findPath(input: List<String>, lineNo: Int, beamIndex: Int, dpTable: MutableMap<Pair<Int, Int>, Long>): Long {
    if (lineNo >= input.size) return 1

    val line = input[lineNo]
    var ans: Long = 0
    if (line[beamIndex] == '^') {
        val leftEntry = dpTable[lineNo to beamIndex - 1]
        val rightEntry = dpTable[lineNo to beamIndex + 1]
        if (leftEntry != null && rightEntry != null) {
            ans += leftEntry + rightEntry
        } else if (leftEntry != null) {
            val rightPath = findPath(input, lineNo + 2, beamIndex + 1, dpTable)
            ans += leftEntry + rightPath
            dpTable[lineNo to beamIndex + 1] = rightPath
        } else if (rightEntry != null) {
            val leftPath = findPath(input, lineNo + 2, beamIndex - 1, dpTable)
            ans += leftPath + rightEntry
            dpTable[lineNo to beamIndex - 1] = leftPath
        } else {
            val leftPath = findPath(input, lineNo + 2, beamIndex - 1, dpTable)
            val rightPath = findPath(input, lineNo + 2, beamIndex + 1, dpTable)
            ans += leftPath + rightPath
            dpTable[lineNo to beamIndex - 1] = leftPath
            dpTable[lineNo to beamIndex + 1] = rightPath
        }
    } else {
        ans = findPath(input, lineNo + 2, beamIndex, dpTable)
    }

    dpTable[lineNo - 2 to beamIndex] = ans

    return ans
}
