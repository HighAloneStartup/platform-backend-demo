package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
open class SpringBootSecurityJwtMongodbApplication {
}

fun main(args: Array<String>) {
    runApplication<SpringBootSecurityJwtMongodbApplication>(*args)
}