interface Functor<T> {
    fun <R> map(transform: (T) -> R): Functor<R>
}

class Single<T>(val x: T): Functor<T> {

    companion object {
        fun <T> just(y: T): Single<T> = Single(y)
    }

    override fun <R> map(transform: (T) -> R): Functor<R> = Single(transform(x))
}

fun request(): Int = 1
fun requestString(int: Int): String = "Bloder $int"

fun getInt(): Single<Int> = Single.just(request())
    .map { it + 1 } as Single<Int>

fun getString(int: Int): Single<String> = Single.just(requestString(int))

fun main() {
    getInt().map {}
}