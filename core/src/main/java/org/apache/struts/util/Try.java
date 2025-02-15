/*
 * $Id$
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.struts.util;

import java.util.concurrent.Callable;

/**
 * The {@code Try} class represents a computation that may result in a successfully computed value
 * or a checked exception.
 *
 * @param <T> the type of the result produced by the computation
 */
public abstract class Try<T> {

    /**
     * Wraps a checked computation into a {@code Try} instance. If the computation throws an exception,
     * a {@code Failure} containing the exception is returned; otherwise, a {@code Success} with the
     * result is returned.
     *
     * @param callable the checked computation to execute
     * @param <T>      the result type of the computation
     * @return a {@code Try} representing the success or failure of the computation
     */
    public static <T> Try<T> ofCallable(Callable<T> callable) {
        try {
            return new Success<>(callable.call());
        } catch (Exception e) {
            return new Failure<>(e);
        }
    }

    /**
     * Returns the result value if the computation is successful. If this is a {@code Failure}, throws
     * the contained exception.
     *
     * @return the successfully computed value
     * @throws Exception if this is a {@code Failure}, wrapping the original checked exception
     */
    public abstract T get() throws Exception;

    /**
     * Represents result of a successfully computed value
     *
     * @param <T> the type of the result produced by the computation
     */
    public static class Success<T> extends Try<T> {
        private final T result;

        public Success(T result) {
            this.result = result;
        }

        @Override
        public T get() {
            return result;
        }
    }

    /**
     * Represents an exception
     *
     * @param <T> the type of the result produced by the computation
     */
    public static class Failure<T> extends Try<T> {
        private final Exception e;

        public Failure(Exception e) {
            this.e = e;
        }

        @Override
        public T get() throws Exception {
           throw e;
        }
    }
}