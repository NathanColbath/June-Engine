package june.util;

import java.util.ArrayList;

public class Log {

    private ArrayList<LogEvent> logEvents;

    public Log(){
        logEvents = new ArrayList<LogEvent>();
    }

    public void syslog(String log){
        LogEvent logEvent = new LogEvent(log,LogLevel.LOG_LEVEL);
        System.out.println(logEvent);
        logEvents.add(logEvent);
    }

    public void syserror(String error){
        logEvents.add(new LogEvent(error,LogLevel.ERROR_LEVEL));
    }

    public void syswarn(String warn){
        logEvents.add(new LogEvent(warn,LogLevel.WARN_LEVEL));
    }

    public void sysfatal(String error){
        logEvents.add(new LogEvent(error,LogLevel.FATAL_LEVEL));
    }

    public ArrayList<LogEvent> getLogEvents(){
        return logEvents;
    }

    public void error(String msg){
        System.err.println("[ERROR]: " + msg);
    }

}
