package june.util;

import java.time.LocalTime;

public class LogEvent {

    private String message;
    private LogLevel level;
    private LocalTime logTime;

    public LogEvent(String message, LogLevel level) {
        this.message = message;
        this.level = level;
        logTime = LocalTime.now();
    }

    public LogLevel getLevel() {
        return level;
    }

    public String getMessage() {
        return message;

    }

    public LocalTime getLogTime() {
        return logTime;
    }
}
