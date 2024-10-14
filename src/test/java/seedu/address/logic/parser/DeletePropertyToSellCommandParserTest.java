package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeletePropertyToSellCommand;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROPERTY_TO_SELL;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside the DeletePropertyToSellCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeletePropertyToSellCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeletePropertyToSellCommandParserTest {

    private DeletePropertyToSellCommandParser parser = new DeletePropertyToSellCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1 1",
                new DeletePropertyToSellCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PROPERTY_TO_SELL));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePropertyToSellCommand.MESSAGE_USAGE));
    }
}
