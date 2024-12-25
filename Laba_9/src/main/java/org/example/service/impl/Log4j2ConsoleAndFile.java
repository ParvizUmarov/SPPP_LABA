package org.example.service;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.serviceInterface.LoggerConsole;
import org.springframework.stereotype.Service;

@Service
public class Log4j2ConsoleAndFile implements LoggerConsole {

    private static final Logger logger = LogManager.getLogger(Log4j2ConsoleAndFile.class);

    @Override
    public void logINFO(String message) {
        logger.log(Level.INFO, message);
    }

    @Override
    public void logERROR(String message) {
        logger.log(Level.ERROR, message);
    }

}
