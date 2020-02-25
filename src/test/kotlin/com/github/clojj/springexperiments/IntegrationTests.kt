package com.github.clojj.springexperiments

import com.github.clojj.springexperiments.gol.Cell
import com.github.clojj.springexperiments.gol.World
import com.github.clojj.springexperiments.gol.generate
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.reactive.asPublisher
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import reactor.test.StepVerifier
import java.util.function.Predicate


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests(@Autowired val restTemplate: TestRestTemplate) {

    @Test
    fun `Assert world content and status code`() {
        val entity = restTemplate.getForEntity<String>("/gol/world?n=5")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        println("entity $entity")

        (1..5).map { println("entity ${restTemplate.getForEntity<String>("/gol/world?n=$it")}") }
    }

}

class ReactorTests() {

    private val blinker = World("dummy", setOf(Cell(1, 1), Cell(2, 1), Cell(3, 1)))

    val source: Flow<List<Cell>> = flow {
        var n = 0
        val start = System.currentTimeMillis()
        while (n < 4) {
            delay(500)
            println(System.currentTimeMillis() - start)
            emit(generate(blinker, n++).cells.toList())
        }

    }

    @Test
    fun `Game-of-life with a 'blinker'`() {
        StepVerifier
                .create(source.asPublisher())
                .expectNext(listOf(Cell(x=1, y=1), Cell(x=2, y=1), Cell(x=3, y=1)))
                .expectNext(listOf(Cell(x=2, y=0), Cell(x=2, y=1), Cell(x=2, y=2)))
                .expectNext(listOf(Cell(x=1, y=1), Cell(x=2, y=1), Cell(x=3, y=1)))
                .expectNext(listOf(Cell(x=2, y=0), Cell(x=2, y=1), Cell(x=2, y=2)))
                .expectComplete()
                .verify()
    }

}
