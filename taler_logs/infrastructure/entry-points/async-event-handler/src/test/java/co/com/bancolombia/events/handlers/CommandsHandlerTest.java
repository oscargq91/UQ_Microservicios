package co.com.bancolombia.events.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactivecommons.api.domain.Command;
import reactor.test.StepVerifier;


import java.util.UUID;

public class CommandsHandlerTest {

    CommandsHandler commandsHandler;

    @BeforeEach
    void setUp() {
        commandsHandler = new CommandsHandler();
    }

     @Test
        void handleCommandATest(){
            StepVerifier.create(commandsHandler.handleCommandA(
                new Command<>("COMMAND",
                    UUID.randomUUID().toString(),
                        "Data"))).expectComplete();
        }
}
