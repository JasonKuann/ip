package blitz.parser;

import org.junit.jupiter.api.Test;
import blitz.command.Command;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void parseTodo() throws Exception {
        Command c = Parser.parse("todo read book");
        assertNotNull(c);
        assertEquals("AddTodoCommand", c.getClass().getSimpleName());
    }
}
