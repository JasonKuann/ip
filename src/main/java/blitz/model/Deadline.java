package blitz.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A task that has a deadline.
 *
 * <p>Parses user-provided date strings into LocalDateTime when possible.
 */
public class Deadline extends Task {

    protected final String by;

    /**
     * Create a Deadline.
     *
     * @param description human description of the task
     * @param dateStr user-provided date string (e.g., "2019-12-02" or "2/12/2019 1800")
     */
    public Deadline(final String description, final String dateStr) {
        super(description);
        this.by = dateStr;
        this.date = parseDate(dateStr);
    }

    @Override
    public String getTaskInfo() {
        if (date == null) {
            return "by: " + by;
        }

        final DateTimeFormatter dateOnly = DateTimeFormatter.ofPattern("MMM d yyyy");      // e.g. Dec 2 2019
        if (date.getHour() == 0 && date.getMinute() == 0) {
            return "by: " + date.format(dateOnly);
        } else {
            final DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm"); // e.g. Dec 2 2019 18:00
            return "by: " + date.format(dateTime);
        }
    }

    /**
     * Attempts to parse common date formats into LocalDateTime.
     *
     * Accepted formats:
     * <ul>
     *   <li>yyyy-MM-dd (interpreted as start of day)</li>
     *   <li>d/M/yyyy HHmm (e.g., 2/12/2019 1800)</li>
     *   <li>d/M/yyyy (interpreted as start of day)</li>
     * </ul>
     *
     * If parsing fails, returns null and the original string is preserved in {@link #by}.
     *
     * @param dateStr the date/time string provided by the user
     * @return parsed LocalDateTime or null when parsing failed
     */
    private LocalDateTime parseDate(final String dateStr) {
        if (dateStr == null) {
            return null;
        }
        final String s = dateStr.trim();

        // Try ISO yyyy-MM-dd first
        try {
            final LocalDate ld = LocalDate.parse(s); // accepts "2019-12-02"
            return ld.atStartOfDay();
        } catch (Exception ignored) {
            // try next
        }

        // Try d/M/yyyy HHmm (e.g. "2/12/2019 1800")
        try {
            final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return LocalDateTime.parse(s, dtf);
        } catch (Exception ignored) {
            // try next
        }

        // Try d/M/yyyy (e.g. "2/12/2019")
        try {
            final DateTimeFormatter df = DateTimeFormatter.ofPattern("d/M/yyyy");
            final LocalDate ld = LocalDate.parse(s, df);
            return ld.atStartOfDay();
        } catch (Exception ignored) {
            // nothing left
        }

        // Could not parse — preserve original string
        return null;
    }

    /** Returns the original user-provided date string. */
    public String getBy() {
        return this.by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (" + getTaskInfo() + ")";
    }
}
