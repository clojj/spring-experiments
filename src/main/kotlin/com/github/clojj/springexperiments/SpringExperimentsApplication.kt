package com.github.clojj.springexperiments

import com.github.clojj.springexperiments.gol.GolProperties
import com.github.clojj.springexperiments.gol.World
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@EnableConfigurationProperties(GolProperties::class)
class SpringExperimentsApplication

fun main(args: Array<String>) {
    runApplication<SpringExperimentsApplication>(*args)
}

