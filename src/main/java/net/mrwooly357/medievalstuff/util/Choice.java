package net.mrwooly357.medievalstuff.util;

@FunctionalInterface
public interface Choice<A> {

    Choice<Object> FIRST = (first, second) -> first;
    Choice<Object> SECOND = (first, second) -> second;


    A choose(A first, A second);
}
