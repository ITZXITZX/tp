package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTUAL_PRICE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Price;
import seedu.address.model.person.Property;

/**
 * Records the {@code Property} sold.
 */
public class SoldPropertyCommand extends Command {
    public static final String COMMAND_WORD = "sold";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Records the property that has been sold "
            + "using the index number of the person and index number of the property-to-sell in the displayed "
            + "persons list. Removes this property from the property-to-sell list.\n"
            + "Parameters: PERSON_INDEX (must be a positive integer) "
            + "PROPERTY_TO_SELL_INDEX (must be a positive integer) "
            + PREFIX_ACTUAL_PRICE + "[ACTUAL_PRICE]...\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 1 "
            + PREFIX_ACTUAL_PRICE + "1100000";

    public static final String MESSAGE_SUCCESS = "Record property as sold: %1$s";

    private final Index personIndex;
    private final Index propertyIndex;
    private final Price actualPrice;

    /**
     * Creates an SoldPropertyCommand to record and remove the specified {@code Property}
     * in the list of properties to sell of the specified contact {@code Index}.
     *
     * @param personIndex of the person in the filtered person list to edit
     * @param propertyIndex of the property to be marked as sold in the list of properties to sell
     * @param actualPrice An {@code Optional} of the actual price of the property sold if provided by the user
     */
    public SoldPropertyCommand(Index personIndex, Index propertyIndex, Price actualPrice) {
        requireNonNull(personIndex);
        requireNonNull(propertyIndex);
        requireNonNull(actualPrice);

        this.personIndex = personIndex;
        this.propertyIndex = propertyIndex;
        this.actualPrice = actualPrice;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(personIndex.getZeroBased());

        if (!personToEdit.isValidSellingPropertyIndex(propertyIndex)) {
            throw new CommandException((Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX));
        }

        Property updatedProperty = personToEdit.getSoldProperty(personIndex, actualPrice);
        personToEdit.updateSoldProperty(updatedProperty, propertyIndex);

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatProperty(updatedProperty)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SoldPropertyCommand // instanceof handles nulls
                && personIndex.equals(((SoldPropertyCommand) other).personIndex)
                && propertyIndex.equals(((SoldPropertyCommand) other).propertyIndex)
                && actualPrice.equals(((SoldPropertyCommand) other).actualPrice));
    }
}