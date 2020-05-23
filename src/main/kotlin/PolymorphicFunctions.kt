fun <T> polymorphicFunction(x: T): T {
    print(x)
    return x
}

fun polymorphicFunction(x: String) {
    print(x)
}

fun String.print2() {
    print(this)
}

fun <T> T.print2() : T {
    print(this)
    return this
}

fun main() {
    "Bloder".print2()
    1.print2()
    2.2.print2()
}
