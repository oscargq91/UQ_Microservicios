package co.com.bancolombia.events.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class QueriesHandlerTest {

    QueriesHandler queriesHandler;

    @BeforeEach
    void setUp() {
        queriesHandler = new QueriesHandler();
    }
    @Test
        void QueriesHandlerTest(){
            StepVerifier.create(queriesHandler
                    .handleQueryA("Data"))
                .expectComplete();
        }
}
