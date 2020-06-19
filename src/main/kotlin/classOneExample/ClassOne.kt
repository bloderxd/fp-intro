package classOneExample

import java.lang.Exception
import java.util.*
import kotlin.concurrent.schedule


// HTTP RESULT
class HttpResult<T>(private val result: Result<T>) {
    companion object {
        fun <T> success(value: T): HttpResult<T> = HttpResult(Result.success(value))
        fun <T> error(error: Exception): HttpResult<T> = HttpResult(Result.failure(error))
    }

    val respose: () -> T = {
        when {
            result.isSuccess -> result.getOrThrow()
            else -> throw Exception()
        }
    }

    fun safeResponse(): Continuation<T> = Continuation { respose() }
}


// CONTINUATION
class Continuation<T>(val run: () -> T)

infix fun <T> Continuation<T>.or(defaultValue: (error: Exception) -> T): T = try {
    this.run()
} catch (e: Exception) {
    defaultValue(e)
}


// USER
data class User(val userName: UserName, val password: Password)

typealias UserName = String
typealias Password = String

val loginRequest: (User) -> HttpResult<Boolean> = { HttpResult.error(Exception()) }
val registerRequest: (User) -> HttpResult<Boolean> = { HttpResult.success(true) }


val loginUseCase: (UserName) -> (Password) -> ((user: User) -> Unit) -> Boolean = { userName ->
    { password ->
        { orExecute ->
            val user = User(userName, password)
            loginRequest(user).safeResponse() or { error ->
                // Error used in tracking for example

                orExecute(user)
                false
            }
        }
    }
}

val registerUseCase: (User) -> Unit = { user ->
    val result = registerRequest(user).safeResponse() or { false }
    println("Login failed, Registration $result")
}

val doWait: (Long, () -> Unit) -> Unit = { delay, block ->
    Timer().schedule(delay) {
        block()
    }
}

// MAIN
val x: () -> () -> Int = { { 1 } }

fun y(z: Int) {}

fun main() {
    val flowUserName = loginUseCase("Bloder")
    val flowPassword = flowUserName("123456")

    println("Login ${flowPassword { user -> doWait(200) { registerUseCase(user) } }}")
}