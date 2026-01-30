package blitz.model;

/**
 * An event with a start and end time (stored as strings).
 */
public class Event extends Task {

    private final String startTime;
    private final String endTime;

    /**
     * Construct an Event.
     *
     * @param description event description
     * @param startTime   start time (free text)
     * @param endTime     end time (free text)
     */
    public Event(final String description, final String startTime, final String endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /** Return the start time string. */
    public String getStartTime() {
        return startTime;
    }

    /** Return the end time string. */
    public String getEndTime() {
        return endTime;
    }

    @Override
    public String getTaskInfo() {
        return "from: " + startTime + " to: " + endTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startTime + " to: " + endTime + ")";
    }
}
