/*
 * Copyright 2015 Jin Kwon &lt;jinahya_at_gmail.com&gt;.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.github.jinahya.persistence;


import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
@Dependent
public class EntityManagerSupport {


    public static void accept(final EntityManager manager,
                              final Consumer<EntityManager> consumer) {

        consumer.accept(manager);
    }


    public static <U> void accept(final EntityManager manager, final U u,
                                  final BiConsumer<EntityManager, U> consumer) {

        consumer.accept(manager, u);
    }


    public static <R> R apply(final EntityManager manager,
                              final Function<EntityManager, R> function) {

        return function.apply(manager);
    }


    public static <U, R> R apply(
        final EntityManager manager, final U u,
        final BiFunction<EntityManager, U, R> function) {

        return function.apply(manager, u);
    }


    public static double applyAsDouble(
        final EntityManager manager,
        final ToDoubleFunction<EntityManager> function) {

        return function.applyAsDouble(manager);
    }


    public static <U> double applyAsDouble(
        final EntityManager manager, final U u,
        final ToDoubleBiFunction<EntityManager, U> function) {

        return function.applyAsDouble(manager, u);
    }


    public static int applyAsDouble(
        final EntityManager manager,
        final ToIntFunction<EntityManager> function) {

        return function.applyAsInt(manager);
    }


    public static <U> int applyAsDouble(
        final EntityManager manager, final U u,
        final ToIntBiFunction<EntityManager, U> function) {

        return function.applyAsInt(manager, u);
    }


    public static long applyAsLong(
        final EntityManager manager,
        final ToLongFunction<EntityManager> function) {

        return function.applyAsLong(manager);
    }


    public static <U> long applyAsLong(
        final EntityManager manager, final U u,
        final ToLongBiFunction<EntityManager, U> function) {

        return function.applyAsLong(manager, u);
    }


    public static boolean test(final EntityManager manager,
                               final Predicate<EntityManager> predicate) {

        return predicate.test(manager);
    }


    public static <U> boolean test(
        final EntityManager manager, final U u,
        final BiPredicate<EntityManager, U> predicate) {

        return predicate.test(manager, u);
    }


    public void accept(final Consumer<EntityManager> consumer) {

        accept(entityManager, consumer);
    }


    public <U> void accept(final U u,
                           final BiConsumer<EntityManager, U> consumer) {

        accept(entityManager, u, consumer);
    }


    public <R> R apply(final Function<EntityManager, R> function) {

        return apply(entityManager, function);
    }


    public <U, R> R apply(final U u,
                          final BiFunction<EntityManager, U, R> function) {

        return apply(entityManager, u, function);
    }


    public double applyAsDouble(
        final ToDoubleFunction<EntityManager> function) {

        return applyAsDouble(entityManager, function);
    }


    public <U> double applyAsDouble(
        final U u, final ToDoubleBiFunction<EntityManager, U> function) {

        return applyAsDouble(entityManager, u, function);
    }


    public int applyAsDouble(final ToIntFunction<EntityManager> function) {

        return applyAsDouble(entityManager, function);
    }


    public <U> int applyAsDouble(
        final U u, final ToIntBiFunction<EntityManager, U> function) {

        return applyAsDouble(entityManager, u, function);
    }


    public long applyAsLong(final ToLongFunction<EntityManager> function) {

        return applyAsLong(entityManager, function);
    }


    public <U> long applyAsLong(
        final U u, final ToLongBiFunction<EntityManager, U> function) {

        return applyAsLong(entityManager, u, function);
    }


    public boolean test(final Predicate<EntityManager> predicate) {

        return test(entityManager, predicate);
    }


    public <U> boolean test(final U u,
                            final BiPredicate<EntityManager, U> predicate) {

        return test(entityManager, u, predicate);
    }


    public EntityManager getEntityManager() {

        return entityManager;
    }


    public void setEntityManager(final EntityManager entityManager) {

        this.entityManager = entityManager;
    }


    private transient EntityManager entityManager;


}

