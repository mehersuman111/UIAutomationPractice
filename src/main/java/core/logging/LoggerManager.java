package core.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerManager {

    private final Logger logger;

    public LoggerManager(Class<?> clazz){
        logger = LogManager.getLogger(clazz);
    }

    public LoggerManager logMessage(String logLevel,String message){
        Level level = Level.getLevel(logLevel.toUpperCase());

        if(level == null){
            throw new IllegalArgumentException("Invalid Log level provided i.e., " + logLevel);
        }

        logger.log(Level.getLevel(logLevel),message);

        return this;
    }
}
