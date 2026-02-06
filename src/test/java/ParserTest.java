package blitz.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import blitz.command.Command;



public class ParserTest {

    @Test
    public void parseTodo() throws Exception {
        Command c = Parser.parse("todo read book");
        assertNotNull(c);
        assertEquals("AddTodoCommand", c.getClass().getSimpleName());
    }
}
