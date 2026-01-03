import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        val split = input.indexOfFirst { it.isEmpty() }
        val (rangesRaw, ingredientsRaw) = input.take(split) to input.drop(split + 1)

        val ranges = buildSet<Pair<Long, Long>> {
            rangesRaw.forEach { rangeRaw ->
                val range = rangeRaw.split('-')
                val lower = range[0].toLong()
                val upper = range[1].toLong()

                val toRemove: MutableSet<Pair<Long, Long>> = mutableSetOf()
                var newRange = lower to upper
                forEach { range ->
                    if (
                        (newRange.first >= range.first && newRange.first <= range.second) ||
                        (newRange.second >= range.first && newRange.second <= range.second) ||
                        (range.first >= newRange.first && range.first <= newRange.second) ||
                        (range.second >= newRange.first && range.second <= newRange.second)
                    ) {
                        newRange = min(newRange.first, range.first) to max(newRange.second, range.second)
                        if (newRange != range) {
                            toRemove.add(range)
                        }
                    }
                }

                add(newRange)
                toRemove.forEach { remove(it) }
            }
        }

        return ingredientsRaw.count { ingredientRaw ->
            val ingredient = ingredientRaw.toLong()
            ranges.any { range -> ingredient >= range.first && ingredient <= range.second }
        }
    }

    fun part2(input: List<String>): Long {
        val split = input.indexOfFirst { it.isEmpty() }
        val rangesRaw = input.take(split)

        val ranges = buildSet<Pair<Long, Long>> {
            rangesRaw.forEach { rangeRaw ->
                val range = rangeRaw.split('-')
                val lower = range[0].toLong()
                val upper = range[1].toLong()

                val toRemove: MutableSet<Pair<Long, Long>> = mutableSetOf()
                var newRange = lower to upper
                forEach { range ->
                    if (
                        (newRange.first >= range.first && newRange.first <= range.second) ||
                        (newRange.second >= range.first && newRange.second <= range.second) ||
                        (range.first >= newRange.first && range.first <= newRange.second) ||
                        (range.second >= newRange.first && range.second <= newRange.second)
                    ) {
                        newRange = min(newRange.first, range.first) to max(newRange.second, range.second)
                        if (newRange != range) {
                            toRemove.add(range)
                        }
                    }
                }

                add(newRange)
                toRemove.forEach { remove(it) }
            }
        }

        return ranges.fold(
            initial = 0L,
            operation = { acc, el ->
                acc + (el.second - el.first + 1)
            }
        )
    }

    // Or read a large test input from the `src/input/Day05_test.txt` file:
    val testInput = readInput("Day05_test")
//    println("Test Part1 Day05: ${part1(testInput)}")
    println("Test Part2 Day05: ${part2(testInput)}")

    // Read the input from the `src/input/Day05.txt` file.
    val input = readInput("Day05")
//    println("Part1 Day05: ${part1(input)}")
    println("Part2 Day05: ${part2(input)}")
}
