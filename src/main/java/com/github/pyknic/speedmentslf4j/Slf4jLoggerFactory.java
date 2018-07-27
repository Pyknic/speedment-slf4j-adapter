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

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.speedment.common.logger.internal.util.NullUtil.requireNonNulls;
import static java.util.Collections.newSetFromMap;
import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class Slf4jLoggerFactory implements LoggerFactory {

    private LoggerFormatter formatter;
    private Level level;

    private final Map<String, Logger> loggers;
    private final Set<LoggerEventListener> listeners;

    public Slf4jLoggerFactory() {
        this.loggers   = new ConcurrentHashMap<>();
        this.formatter = new Slf4jLoggerFormatter();
        this.level     = Level.defaultLevel();
        this.listeners = newSetFromMap(new ConcurrentHashMap<>());
    }

    @Override
    public Logger create(Class<?> binding) {
        return prepare(binding, org.slf4j.LoggerFactory.getLogger(binding));
    }

    @Override
    public Logger create(String binding) {
        return prepare(null, org.slf4j.LoggerFactory.getLogger(binding));
    }

    @Override
    public Class<? extends Logger> loggerClass() {
        return Slf4jLogger.class;
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
        if (listeners.add(listener)) {
            forEachLogger(log -> log.addListener(listener));
        }
    }

    @Override
    public void removeListener(LoggerEventListener listener) {
        if (listeners.remove(listener)) {
            forEachLogger(log -> log.removeListener(listener));
        }
    }

    @Override
    public Stream<Map.Entry<String, Logger>> loggers() {
        return loggers.entrySet().stream();
    }

    @Override
    public Stream<LoggerEventListener> listeners() {
        return listeners.stream();
    }

    @Override
    public void setLevel(String path, Level level) {
        requireNonNulls(path, level);
        loggers().filter(e -> e.getKey().startsWith(path))
            .map(Map.Entry::getValue)
            .forEach((Logger l) -> l.setLevel(level)
        );
    }

    @Override
    public void setLevel(Class<?> binding, Level level) {
        requireNonNulls(binding, level);
        setLevel(makeNameFrom(binding), level);
    }

    private Logger prepare(Class<?> clazz, org.slf4j.Logger inner) {
        // clazz is nullable
        final Logger log = new Slf4jLogger(inner, formatter, level);
        listeners.forEach(log::addListener);
        loggers.put(clazz == null ? inner.getName() : makeNameFrom(clazz), log);
        return log;
    }

    private void forEachLogger(Consumer<Logger> action) {
        loggers().map(Map.Entry::getValue).forEach(action);
    }

    private String makeNameFrom(Class<?> binding) {
        requireNonNull(binding);
        final String[] tokens = binding.getName().split("\\.");
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tokens.length; i++) {
            if (i == tokens.length - 1) {
                sb.append(tokens[i]);
            } else {
                sb.append(tokens[i].charAt(0)).append('.');
            }
        }
        return sb.toString();
    }

    private Logger acquireLogger(String binding) {
        return loggers.computeIfAbsent(binding, this::create);
    }
}
