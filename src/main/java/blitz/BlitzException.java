package blitz;

/**
 * Simple checked exception for invalid Blitz commands or inputs.
 */
public class BlitzException extends Exception {

    /**
     * Create a new BlitzException with the given message.
     *
     * @param message detailed error message
     */
    public BlitzException(final String message) {
        super(message);
    }
}
