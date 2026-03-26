package com.yelstream.topp.standard.logging.slf4j.spi.logger;

import com.yelstream.topp.standard.logging.slf4j.event.Levels;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.Level;

public class EnabledLogger extends ProxyLogger {

    /**
     *
     */
    private final Level level;

    protected EnabledLogger(Logger logger,
                            Level level) {
        super(logger);
        this.level=level;
    }

    private boolean enabled(Level level) {
        return Levels.isLevelEnabled(level,this.level);
    }

    private void log(Level level, Runnable action) {
        if (enabled(level)) {
            action.run();
        }
    }

    // --------------------------------------------------
    // Basic
    // --------------------------------------------------

    @Override
    public String getName() {
        String name=super.getName();
        return name!=null?name:"EnableLogger";
    }

    // --------------------------------------------------
    // TRACE
    // --------------------------------------------------

    @Override
    public boolean isTraceEnabled() {
        return enabled(Level.TRACE);
    }

    @Override
    public void trace(String msg) {
        log(Level.TRACE, () -> super.trace(msg));
    }

    @Override
    public void trace(String format, Object arg) {
        log(Level.TRACE, () -> super.trace(format, arg));
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        log(Level.TRACE, () -> super.trace(format, arg1, arg2));
    }

    @Override
    public void trace(String format, Object... arguments) {
        log(Level.TRACE, () -> super.trace(format, arguments));
    }

    @Override
    public void trace(String msg, Throwable t) {
        log(Level.TRACE, () -> super.trace(msg, t));
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return enabled(Level.TRACE);
    }

    @Override
    public void trace(Marker marker, String msg) {
        log(Level.TRACE, () -> super.trace(marker, msg));
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        log(Level.TRACE, () -> super.trace(marker, format, arg));
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        log(Level.TRACE, () -> super.trace(marker, format, arg1, arg2));
    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {
        log(Level.TRACE, () -> super.trace(marker, format, argArray));
    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        log(Level.TRACE, () -> super.trace(marker, msg, t));
    }

    // --------------------------------------------------
    // DEBUG
    // --------------------------------------------------

    @Override
    public boolean isDebugEnabled() {
        return enabled(Level.DEBUG);
    }

    @Override
    public void debug(String msg) {
        log(Level.DEBUG, () -> super.debug(msg));
    }

    @Override
    public void debug(String format, Object arg) {
        log(Level.DEBUG, () -> super.debug(format, arg));
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        log(Level.DEBUG, () -> super.debug(format, arg1, arg2));
    }

    @Override
    public void debug(String format, Object... arguments) {
        log(Level.DEBUG, () -> super.debug(format, arguments));
    }

    @Override
    public void debug(String msg, Throwable t) {
        log(Level.DEBUG, () -> super.debug(msg, t));
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return enabled(Level.DEBUG);
    }

    @Override
    public void debug(Marker marker, String msg) {
        log(Level.DEBUG, () -> super.debug(marker, msg));
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        log(Level.DEBUG, () -> super.debug(marker, format, arg));
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        log(Level.DEBUG, () -> super.debug(marker, format, arg1, arg2));
    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {
        log(Level.DEBUG, () -> super.debug(marker, format, arguments));
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        log(Level.DEBUG, () -> super.debug(marker, msg, t));
    }

    // --------------------------------------------------
    // INFO
    // --------------------------------------------------

    @Override
    public boolean isInfoEnabled() {
        return enabled(Level.INFO);
    }

    @Override
    public void info(String msg) {
        log(Level.INFO, () -> super.info(msg));
    }

    @Override
    public void info(String format, Object arg) {
        log(Level.INFO, () -> super.info(format, arg));
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        log(Level.INFO, () -> super.info(format, arg1, arg2));
    }

    @Override
    public void info(String format, Object... arguments) {
        log(Level.INFO, () -> super.info(format, arguments));
    }

    @Override
    public void info(String msg, Throwable t) {
        log(Level.INFO, () -> super.info(msg, t));
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return enabled(Level.INFO);
    }

    @Override
    public void info(Marker marker, String msg) {
        log(Level.INFO, () -> super.info(marker, msg));
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        log(Level.INFO, () -> super.info(marker, format, arg));
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        log(Level.INFO, () -> super.info(marker, format, arg1, arg2));
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {
        log(Level.INFO, () -> super.info(marker, format, arguments));
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        log(Level.INFO, () -> super.info(marker, msg, t));
    }

    // --------------------------------------------------
    // WARN
    // --------------------------------------------------

    @Override
    public boolean isWarnEnabled() {
        return enabled(Level.WARN);
    }

    @Override
    public void warn(String msg) {
        log(Level.WARN, () -> super.warn(msg));
    }

    @Override
    public void warn(String format, Object arg) {
        log(Level.WARN, () -> super.warn(format, arg));
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        log(Level.WARN, () -> super.warn(format, arg1, arg2));
    }

    @Override
    public void warn(String format, Object... arguments) {
        log(Level.WARN, () -> super.warn(format, arguments));
    }

    @Override
    public void warn(String msg, Throwable t) {
        log(Level.WARN, () -> super.warn(msg, t));
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return enabled(Level.WARN);
    }

    @Override
    public void warn(Marker marker, String msg) {
        log(Level.WARN, () -> super.warn(marker, msg));
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        log(Level.WARN, () -> super.warn(marker, format, arg));
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        log(Level.WARN, () -> super.warn(marker, format, arg1, arg2));
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {
        log(Level.WARN, () -> super.warn(marker, format, arguments));
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        log(Level.WARN, () -> super.warn(marker, msg, t));
    }

    // --------------------------------------------------
    // ERROR
    // --------------------------------------------------

    @Override
    public boolean isErrorEnabled() {
        return enabled(Level.ERROR);
    }

    @Override
    public void error(String msg) {
        log(Level.ERROR, () -> super.error(msg));
    }

    @Override
    public void error(String format, Object arg) {
        log(Level.ERROR, () -> super.error(format, arg));
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        log(Level.ERROR, () -> super.error(format, arg1, arg2));
    }

    @Override
    public void error(String format, Object... arguments) {
        log(Level.ERROR, () -> super.error(format, arguments));
    }

    @Override
    public void error(String msg, Throwable t) {
        log(Level.ERROR, () -> super.error(msg, t));
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return enabled(Level.ERROR);
    }

    @Override
    public void error(Marker marker, String msg) {
        log(Level.ERROR, () -> super.error(marker, msg));
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        log(Level.ERROR, () -> super.error(marker, format, arg));
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        log(Level.ERROR, () -> super.error(marker, format, arg1, arg2));
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {
        log(Level.ERROR, () -> super.error(marker, format, arguments));
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        log(Level.ERROR, () -> super.error(marker, msg, t));
    }
}
