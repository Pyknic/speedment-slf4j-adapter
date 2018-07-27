/**
 *
 * Copyright (c) 2018, Emil Forslund. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.pyknic.speedmentslf4j;

import com.speedment.common.logger.*;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;
import static java.util.Collections.newSetFromMap;
import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class Slf4jLogger implements Logger {

    private Level level;
    private LoggerFormatter formatter; // Not used by this implementation, since Slf4j formats the logs
    private org.slf4j.Logger inner;    // All operations are delegated to this instance.

    private final String name;
    private final Set<LoggerEventListener> listeners;

    Slf4jLogger(org.slf4j.Logger inner, LoggerFormatter formatter, Level level) {
        this.inner     = requireNonNull(inner);
        this.name      = requireNonNull(inner.getName());
        this.formatter = requireNonNull(formatter);
        this.level     = requireNonNull(level);
        this.listeners = newSetFromMap(new ConcurrentHashMap<>());
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public void setLevel(Level level) {
        this.level = requireNonNull(level);
    }

    @Override
    public void setFormatter(LoggerFormatter formatter) {
        this.formatter = requireNonNull(formatter);
    }

    @Override
    public LoggerFormatter getFormatter() {
        return formatter;
    }

    @Override
    public void addListener(LoggerEventListener listener) {
        listeners.add(requireNonNull(listener));
    }

    @Override
    public void removeListener(LoggerEventListener listener) {
        listeners.remove(requireNonNull(listener));
    }
    
    // Trace

    @Override
    public void trace(String message) {
        if (Level.TRACE.isEqualOrHigherThan(level)) {
            inner.trace(message);
            notifyListeners(Level.TRACE, message);
        }
    }

    @Override
    public void trace(Throwable throwable) {
        if (Level.TRACE.isEqualOrHigherThan(level)) {
            inner.trace("", throwable);
            notifyListeners(Level.TRACE, throwable);
        }
    }

    @Override
    public void trace(String format, Object arg) {
        if (Level.TRACE.isEqualOrHigherThan(level)) {
            inner.trace(format, arg);
            notifyListeners(Level.TRACE, format(format, arg));
        }
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        if (Level.TRACE.isEqualOrHigherThan(level)) {
            inner.trace(format, arg1, arg2);
            notifyListeners(Level.TRACE, format(format, arg1, arg2));
        }
    }

    @Override
    public void trace(String format, Object arg1, Object arg2, Object arg3) {
        if (Level.TRACE.isEqualOrHigherThan(level)) {
            inner.trace(format, arg1, arg2, arg3);
            notifyListeners(Level.TRACE, format(format, arg1, arg2, arg3));
        }
    }

    @Override
    public void trace(String format, Object arg1, Object arg2, Object arg3, Object... args) {
        if (Level.TRACE.isEqualOrHigherThan(level)) {
            final Object[] first = new Object[] {arg1, arg2, arg3};
            log(Level.TRACE, format, first, args);
            notifyListeners(Level.TRACE, format(format, concat(first, args)));
        }
    }

    @Override
    public void trace(Throwable throwable, String message) {
        if (Level.TRACE.isEqualOrHigherThan(level)) {
            inner.trace(message, throwable);
            notifyListeners(Level.TRACE, message, throwable);
        }
    }

    @Override
    public void trace(Throwable throwable, String format, Object arg) {
        if (Level.TRACE.isEqualOrHigherThan(level)) {
            final String msg = format(format, arg);
            inner.trace(msg, throwable);
            notifyListeners(Level.TRACE, msg, throwable);
        }
    }

    @Override
    public void trace(Throwable throwable, String format, Object arg1, Object arg2) {
        if (Level.TRACE.isEqualOrHigherThan(level)) {
            final String msg = format(format, arg1, arg2);
            inner.trace(msg, throwable);
            notifyListeners(Level.TRACE, msg, throwable);
        }
    }

    @Override
    public void trace(Throwable throwable, String format, Object arg1, Object arg2, Object arg3) {
        if (Level.TRACE.isEqualOrHigherThan(level)) {
            final String msg = format(format, arg1, arg2, arg3);
            inner.trace(msg, throwable);
            notifyListeners(Level.TRACE, msg, throwable);
        }
    }

    @Override
    public void trace(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args) {
        if (Level.TRACE.isEqualOrHigherThan(level)) {
            final String msg = format(format, concat(new Object[] {arg1, arg2, arg3}, args));
            inner.trace(msg, throwable);
            notifyListeners(Level.TRACE, msg, throwable);
        }
    }

    // Debug

    @Override
    public void debug(String message) {
        if (Level.DEBUG.isEqualOrHigherThan(level)) {
            inner.debug(message);
            notifyListeners(Level.DEBUG, message);
        }
    }

    @Override
    public void debug(Throwable throwable) {
        if (Level.DEBUG.isEqualOrHigherThan(level)) {
            inner.debug("", throwable);
            notifyListeners(Level.DEBUG, throwable);
        }
    }

    @Override
    public void debug(String format, Object arg) {
        if (Level.DEBUG.isEqualOrHigherThan(level)) {
            inner.debug(format, arg);
            notifyListeners(Level.DEBUG, format(format, arg));
        }
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        if (Level.DEBUG.isEqualOrHigherThan(level)) {
            inner.debug(format, arg1, arg2);
            notifyListeners(Level.DEBUG, format(format, arg1, arg2));
        }
    }

    @Override
    public void debug(String format, Object arg1, Object arg2, Object arg3) {
        if (Level.DEBUG.isEqualOrHigherThan(level)) {
            inner.debug(format, arg1, arg2, arg3);
            notifyListeners(Level.DEBUG, format(format, arg1, arg2, arg3));
        }
    }

    @Override
    public void debug(String format, Object arg1, Object arg2, Object arg3, Object... args) {
        if (Level.DEBUG.isEqualOrHigherThan(level)) {
            final Object[] first = new Object[] {arg1, arg2, arg3};
            log(Level.DEBUG, format, first, args);
            notifyListeners(Level.DEBUG, format(format, concat(first, args)));
        }
    }

    @Override
    public void debug(Throwable throwable, String message) {
        if (Level.DEBUG.isEqualOrHigherThan(level)) {
            inner.debug(message, throwable);
            notifyListeners(Level.DEBUG, message, throwable);
        }
    }

    @Override
    public void debug(Throwable throwable, String format, Object arg) {
        if (Level.DEBUG.isEqualOrHigherThan(level)) {
            final String msg = format(format, arg);
            inner.debug(msg, throwable);
            notifyListeners(Level.DEBUG, msg, throwable);
        }
    }

    @Override
    public void debug(Throwable throwable, String format, Object arg1, Object arg2) {
        if (Level.DEBUG.isEqualOrHigherThan(level)) {
            final String msg = format(format, arg1, arg2);
            inner.debug(msg, throwable);
            notifyListeners(Level.DEBUG, msg, throwable);
        }
    }

    @Override
    public void debug(Throwable throwable, String format, Object arg1, Object arg2, Object arg3) {
        if (Level.DEBUG.isEqualOrHigherThan(level)) {
            final String msg = format(format, arg1, arg2, arg3);
            inner.debug(msg, throwable);
            notifyListeners(Level.DEBUG, msg, throwable);
        }
    }

    @Override
    public void debug(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args) {
        if (Level.DEBUG.isEqualOrHigherThan(level)) {
            final String msg = format(format, concat(new Object[] {arg1, arg2, arg3}, args));
            inner.debug(msg, throwable);
            notifyListeners(Level.DEBUG, msg, throwable);
        }
    }

    // Info

    @Override
    public void info(String message) {
        if (Level.INFO.isEqualOrHigherThan(level)) {
            inner.info(message);
            notifyListeners(Level.INFO, message);
        }
    }

    @Override
    public void info(Throwable throwable) {
        if (Level.INFO.isEqualOrHigherThan(level)) {
            inner.info("", throwable);
            notifyListeners(Level.INFO, throwable);
        }
    }

    @Override
    public void info(String format, Object arg) {
        if (Level.INFO.isEqualOrHigherThan(level)) {
            inner.info(format, arg);
            notifyListeners(Level.INFO, format(format, arg));
        }
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        if (Level.INFO.isEqualOrHigherThan(level)) {
            inner.info(format, arg1, arg2);
            notifyListeners(Level.INFO, format(format, arg1, arg2));
        }
    }

    @Override
    public void info(String format, Object arg1, Object arg2, Object arg3) {
        if (Level.INFO.isEqualOrHigherThan(level)) {
            inner.info(format, arg1, arg2, arg3);
            notifyListeners(Level.INFO, format(format, arg1, arg2, arg3));
        }
    }

    @Override
    public void info(String format, Object arg1, Object arg2, Object arg3, Object... args) {
        if (Level.INFO.isEqualOrHigherThan(level)) {
            final Object[] first = new Object[] {arg1, arg2, arg3};
            log(Level.INFO, format, first, args);
            notifyListeners(Level.INFO, format(format, concat(first, args)));
        }
    }

    @Override
    public void info(Throwable throwable, String message) {
        if (Level.INFO.isEqualOrHigherThan(level)) {
            inner.info(message, throwable);
            notifyListeners(Level.INFO, message, throwable);
        }
    }

    @Override
    public void info(Throwable throwable, String format, Object arg) {
        if (Level.INFO.isEqualOrHigherThan(level)) {
            final String msg = format(format, arg);
            inner.info(msg, throwable);
            notifyListeners(Level.INFO, msg, throwable);
        }
    }

    @Override
    public void info(Throwable throwable, String format, Object arg1, Object arg2) {
        if (Level.INFO.isEqualOrHigherThan(level)) {
            final String msg = format(format, arg1, arg2);
            inner.info(msg, throwable);
            notifyListeners(Level.INFO, msg, throwable);
        }
    }

    @Override
    public void info(Throwable throwable, String format, Object arg1, Object arg2, Object arg3) {
        if (Level.INFO.isEqualOrHigherThan(level)) {
            final String msg = format(format, arg1, arg2, arg3);
            inner.info(msg, throwable);
            notifyListeners(Level.INFO, msg, throwable);
        }
    }

    @Override
    public void info(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args) {
        if (Level.INFO.isEqualOrHigherThan(level)) {
            final String msg = format(format, concat(new Object[] {arg1, arg2, arg3}, args));
            inner.info(msg, throwable);
            notifyListeners(Level.INFO, msg, throwable);
        }
    }

    // Warn

    @Override
    public void warn(String message) {
        if (Level.WARN.isEqualOrHigherThan(level)) {
            inner.warn(message);
            notifyListeners(Level.WARN, message);
        }
    }

    @Override
    public void warn(Throwable throwable) {
        if (Level.WARN.isEqualOrHigherThan(level)) {
            inner.warn("", throwable);
            notifyListeners(Level.WARN, throwable);
        }
    }

    @Override
    public void warn(String format, Object arg) {
        if (Level.WARN.isEqualOrHigherThan(level)) {
            inner.warn(format, arg);
            notifyListeners(Level.WARN, format(format, arg));
        }
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        if (Level.WARN.isEqualOrHigherThan(level)) {
            inner.warn(format, arg1, arg2);
            notifyListeners(Level.WARN, format(format, arg1, arg2));
        }
    }

    @Override
    public void warn(String format, Object arg1, Object arg2, Object arg3) {
        if (Level.WARN.isEqualOrHigherThan(level)) {
            inner.warn(format, arg1, arg2, arg3);
            notifyListeners(Level.WARN, format(format, arg1, arg2, arg3));
        }
    }

    @Override
    public void warn(String format, Object arg1, Object arg2, Object arg3, Object... args) {
        if (Level.WARN.isEqualOrHigherThan(level)) {
            final Object[] first = new Object[] {arg1, arg2, arg3};
            log(Level.WARN, format, first, args);
            notifyListeners(Level.WARN, format(format, concat(first, args)));
        }
    }

    @Override
    public void warn(Throwable throwable, String message) {
        if (Level.WARN.isEqualOrHigherThan(level)) {
            inner.warn(message, throwable);
            notifyListeners(Level.WARN, message, throwable);
        }
    }

    @Override
    public void warn(Throwable throwable, String format, Object arg) {
        if (Level.WARN.isEqualOrHigherThan(level)) {
            final String msg = format(format, arg);
            inner.warn(msg, throwable);
            notifyListeners(Level.WARN, msg, throwable);
        }
    }

    @Override
    public void warn(Throwable throwable, String format, Object arg1, Object arg2) {
        if (Level.WARN.isEqualOrHigherThan(level)) {
            final String msg = format(format, arg1, arg2);
            inner.warn(msg, throwable);
            notifyListeners(Level.WARN, msg, throwable);
        }
    }

    @Override
    public void warn(Throwable throwable, String format, Object arg1, Object arg2, Object arg3) {
        if (Level.WARN.isEqualOrHigherThan(level)) {
            final String msg = format(format, arg1, arg2, arg3);
            inner.warn(msg, throwable);
            notifyListeners(Level.WARN, msg, throwable);
        }
    }

    @Override
    public void warn(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args) {
        if (Level.WARN.isEqualOrHigherThan(level)) {
            final String msg = format(format, concat(new Object[] {arg1, arg2, arg3}, args));
            inner.warn(msg, throwable);
            notifyListeners(Level.WARN, msg, throwable);
        }
    }

    // Error

    @Override
    public void error(String message) {
        if (Level.ERROR.isEqualOrHigherThan(level)) {
            inner.error(message);
            notifyListeners(Level.ERROR, message);
        }
    }

    @Override
    public void error(Throwable throwable) {
        if (Level.ERROR.isEqualOrHigherThan(level)) {
            inner.error("", throwable);
            notifyListeners(Level.ERROR, throwable);
        }
    }

    @Override
    public void error(String format, Object arg) {
        if (Level.ERROR.isEqualOrHigherThan(level)) {
            inner.error(format, arg);
            notifyListeners(Level.ERROR, format(format, arg));
        }
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        if (Level.ERROR.isEqualOrHigherThan(level)) {
            inner.error(format, arg1, arg2);
            notifyListeners(Level.ERROR, format(format, arg1, arg2));
        }
    }

    @Override
    public void error(String format, Object arg1, Object arg2, Object arg3) {
        if (Level.ERROR.isEqualOrHigherThan(level)) {
            inner.error(format, arg1, arg2, arg3);
            notifyListeners(Level.ERROR, format(format, arg1, arg2, arg3));
        }
    }

    @Override
    public void error(String format, Object arg1, Object arg2, Object arg3, Object... args) {
        if (Level.ERROR.isEqualOrHigherThan(level)) {
            final Object[] first = new Object[] {arg1, arg2, arg3};
            log(Level.ERROR, format, first, args);
            notifyListeners(Level.ERROR, format(format, concat(first, args)));
        }
    }

    @Override
    public void error(Throwable throwable, String message) {
        if (Level.ERROR.isEqualOrHigherThan(level)) {
            inner.error(message, throwable);
            notifyListeners(Level.ERROR, message, throwable);
        }
    }

    @Override
    public void error(Throwable throwable, String format, Object arg) {
        if (Level.ERROR.isEqualOrHigherThan(level)) {
            final String msg = format(format, arg);
            inner.error(msg, throwable);
            notifyListeners(Level.ERROR, msg, throwable);
        }
    }

    @Override
    public void error(Throwable throwable, String format, Object arg1, Object arg2) {
        if (Level.ERROR.isEqualOrHigherThan(level)) {
            final String msg = format(format, arg1, arg2);
            inner.error(msg, throwable);
            notifyListeners(Level.ERROR, msg, throwable);
        }
    }

    @Override
    public void error(Throwable throwable, String format, Object arg1, Object arg2, Object arg3) {
        if (Level.ERROR.isEqualOrHigherThan(level)) {
            final String msg = format(format, arg1, arg2, arg3);
            inner.error(msg, throwable);
            notifyListeners(Level.ERROR, msg, throwable);
        }
    }

    @Override
    public void error(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args) {
        if (Level.ERROR.isEqualOrHigherThan(level)) {
            final String msg = format(format, concat(new Object[] {arg1, arg2, arg3}, args));
            inner.error(msg, throwable);
            notifyListeners(Level.ERROR, msg, throwable);
        }
    }

    // Fatal

    @Override
    public void fatal(String message) {
        if (Level.FATAL.isEqualOrHigherThan(level)) {
            inner.error(message);
            notifyListeners(Level.FATAL, message);
        }
    }

    @Override
    public void fatal(Throwable throwable) {
        if (Level.FATAL.isEqualOrHigherThan(level)) {
            inner.error("", throwable);
            notifyListeners(Level.FATAL, throwable);
        }
    }

    @Override
    public void fatal(String format, Object arg) {
        if (Level.FATAL.isEqualOrHigherThan(level)) {
            inner.error(format, arg);
            notifyListeners(Level.FATAL, format(format, arg));
        }
    }

    @Override
    public void fatal(String format, Object arg1, Object arg2) {
        if (Level.FATAL.isEqualOrHigherThan(level)) {
            inner.error(format, arg1, arg2);
            notifyListeners(Level.FATAL, format(format, arg1, arg2));
        }
    }

    @Override
    public void fatal(String format, Object arg1, Object arg2, Object arg3) {
        if (Level.FATAL.isEqualOrHigherThan(level)) {
            inner.error(format, arg1, arg2, arg3);
            notifyListeners(Level.FATAL, format(format, arg1, arg2, arg3));
        }
    }

    @Override
    public void fatal(String format, Object arg1, Object arg2, Object arg3, Object... args) {
        if (Level.FATAL.isEqualOrHigherThan(level)) {
            final Object[] first = new Object[] {arg1, arg2, arg3};
            log(Level.FATAL, format, first, args);
            notifyListeners(Level.FATAL, format(format, concat(first, args)));
        }
    }

    @Override
    public void fatal(Throwable throwable, String message) {
        if (Level.FATAL.isEqualOrHigherThan(level)) {
            inner.error(message, throwable);
            notifyListeners(Level.FATAL, message, throwable);
        }
    }

    @Override
    public void fatal(Throwable throwable, String format, Object arg) {
        if (Level.FATAL.isEqualOrHigherThan(level)) {
            final String msg = format(format, arg);
            inner.error(msg, throwable);
            notifyListeners(Level.FATAL, msg, throwable);
        }
    }

    @Override
    public void fatal(Throwable throwable, String format, Object arg1, Object arg2) {
        if (Level.FATAL.isEqualOrHigherThan(level)) {
            final String msg = format(format, arg1, arg2);
            inner.error(msg, throwable);
            notifyListeners(Level.FATAL, msg, throwable);
        }
    }

    @Override
    public void fatal(Throwable throwable, String format, Object arg1, Object arg2, Object arg3) {
        if (Level.FATAL.isEqualOrHigherThan(level)) {
            final String msg = format(format, arg1, arg2, arg3);
            inner.error(msg, throwable);
            notifyListeners(Level.FATAL, msg, throwable);
        }
    }

    @Override
    public void fatal(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args) {
        if (Level.FATAL.isEqualOrHigherThan(level)) {
            final String msg = format(format, concat(new Object[] {arg1, arg2, arg3}, args));
            inner.error(msg, throwable);
            notifyListeners(Level.FATAL, msg, throwable);
        }
    }

    private void log(Level msgLevel, String format, Object[] first, Object[] then) {
        final Object[] array = concat(first, then);
        switch (msgLevel) {
            case TRACE: inner.trace(format, array); break;
            case DEBUG: inner.debug(format, array); break;
            case INFO:  inner.info(format, array);  break;
            case WARN:  inner.warn(format, array);  break;
            case ERROR: inner.error(format, array); break;
            case FATAL: inner.error(format, array); break;
            default: throw new UnsupportedOperationException();
        }
    }

    private void notifyListeners(Level level, String message) {
        final LoggerEvent ev = new SimpleLoggerEvent(level, name, message);
        listeners.forEach(listener -> listener.accept(ev));
    }

    private void notifyListeners(Level level, Throwable thrw) {
        notifyListeners(level, thrw.getClass().getSimpleName() + ": " + thrw.getMessage());
    }

    private void notifyListeners(Level level, String message, Throwable thrw) {
        notifyListeners(level, thrw.getClass().getSimpleName() + ": " + message);
    }

    private static Object[] concat(Object[] first, Object[] then) {
        final Object[] array = new Object[first.length + then.length];
        System.arraycopy(first, 0, array, 0, first.length);
        System.arraycopy(then, 0, array, first.length, then.length);
        return array;
    }

    private final static class SimpleLoggerEvent implements LoggerEvent {

        private final Level level;
        private final String name, message;

        private SimpleLoggerEvent(Level level, String name, String message) {
            this.level   = requireNonNull(level);
            this.name    = requireNonNull(name);
            this.message = requireNonNull(message);
        }

        @Override
        public Level getLevel() {
            return level;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return format("{level=%s, name='%s', message='%s'}", level, name, message);
        }
    }
}
