val v = 0

fun pure(i: Int, x: Int): Int {
    val u = pure2(i)
    return i + x
}

fun pure2(v1: Int): Int {
    return v1
}

fun main() {
    pure(2, 2)
}