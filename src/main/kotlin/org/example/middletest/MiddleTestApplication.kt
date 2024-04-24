package org.example.middletest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

// 데이터 클래스 정의: User
data class User(var fullName: String, var email: String, var password: String)

@SpringBootApplication
class MiddleTestApplication

fun main(args: Array<String>) {
    runApplication<MiddleTestApplication>(*args)
}

@RestController
class AuthController //회원가입 및 로그인을 처리함
private val users = mutableListOf<User>()

//signup Api
@PostMapping("signup")
fun signup(@RequestBody newUser: User): ResponseEntity<String> {
    val existingUser = users.find { it.email == newUser.email }
    return if (existingUser != null) {
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 사용중인 이메일입니다.")
    } else {
        users.add(newUser)
        ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공")
    }
}

//loginApi
@PostMapping("/login")
fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<String> {
    val user = users.find { it.email == loginRequest.email && it.password == loginRequest.password }
    return if (user != null) {
        ResponseEntity.ok().body("로그인 성공")
    } else {
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("올바른 이메일과 비밀번호를 입력하세요.")
    }
}

// 로그인 요청을 받기 위한 데이터 클래스
data class LoginRequest(val email: String, val password: String)






