import java.util.PriorityQueue
import kotlin.math.sqrt

private data class JunctionBox(val x: Int, val y: Int, val z: Int) {
    fun distance(other: JunctionBox): Double {
        val dx = (x - other.x) * (x - other.x).toDouble()
        val dy = (y - other.y) * (y - other.y).toDouble()
        val dz = (z - other.z) * (z - other.z).toDouble()
        return sqrt((dx + dy + dz))
    }
}

fun main() {
    fun part1(input: List<String>, iterations: Int): Int {
        val junctionBoxes = input.map { rawInput ->
            rawInput
                .split(',')
                .let { JunctionBox(it[0].toInt(), it[1].toInt(), it[2].toInt()) }
        }

        val heap = PriorityQueue<Pair<JunctionBox, JunctionBox>>(compareBy { (a, b) -> a.distance(b) })
        for (i in 0..<junctionBoxes.size) {
            for (j in i + 1..<junctionBoxes.size) {
                heap.add(junctionBoxes[i] to junctionBoxes[j])
            }
        }

        val circuits = junctionBoxes.map { mutableSetOf(it) }.toMutableSet()

        for (i in 0..<iterations) {
            val closest = heap.poll()
            val box1Index = circuits.indexOfFirst { closest.first in it }
            val box2Index = circuits.indexOfFirst { closest.second in it }
            if (box1Index != box2Index) {
                val toRemove1 = circuits.elementAt(box1Index)
                val toRemove2 = circuits.elementAt(box2Index)
                val toAdd = toRemove1.union(toRemove2).toMutableSet()
                circuits.add(toAdd)
                circuits.remove(toRemove1)
                circuits.remove(toRemove2)
            }
        }

        return circuits
            .sortedByDescending { it.size }
            .take(3)
            .map { it.size }
            .fold(initial = 1, operation = Int::times)
    }

    fun part2(input: List<String>): Long {
        val junctionBoxes = input.map { rawInput ->
            rawInput
                .split(',')
                .let { JunctionBox(it[0].toInt(), it[1].toInt(), it[2].toInt()) }
        }

        val heap = PriorityQueue<Pair<JunctionBox, JunctionBox>>(compareBy { (a, b) -> a.distance(b) })
        for (i in 0..<junctionBoxes.size) {
            for (j in i + 1..<junctionBoxes.size) {
                heap.add(junctionBoxes[i] to junctionBoxes[j])
            }
        }

        val circuits = junctionBoxes.map { mutableSetOf(it) }.toMutableSet()
        var ans: Long = 0

        while (circuits.size > 1) {
            val closest = heap.poll()
            val box1Index = circuits.indexOfFirst { closest.first in it }
            val box2Index = circuits.indexOfFirst { closest.second in it }

            ans = closest.first.x.toLong() * closest.second.x.toLong()

            if (box1Index != box2Index) {
                val toRemove1 = circuits.elementAt(box1Index)
                val toRemove2 = circuits.elementAt(box2Index)
                val toAdd = toRemove1.union(toRemove2).toMutableSet()
                circuits.add(toAdd)
                circuits.remove(toRemove1)
                circuits.remove(toRemove2)
            }
        }

        return ans
    }

    // Or read a large test input from the `src/input/Day08_test.txt` file:
    val testInput = readInput("Day08_test")
//    println("Test Part1 Day08: ${part1(input = testInput, iterations = 10)}")
    println("Test Part2 Day08: ${part2(testInput)}")

    // Read the input from the `src/input/Day08.txt` file.
    val input = readInput("Day08")
//    println("Part1 Day08: ${part1(input = input, iterations = 1000)}")
    println("Part2 Day08: ${part2(input)}")
}
