data class User(val userName: UserName, val password: Password)

typealias UserName = String
typealias Password = String

val loginRequest: (UserName) -> (Password) -> User = { userName -> { password ->
    User(userName, password)
}}

val x: () -> () -> Int = { { 1 } }

fun y(z: Int) {}

fun main() {
    val flowLogin = loginRequest("Bloder")
    print(flowLogin("123456"))
}