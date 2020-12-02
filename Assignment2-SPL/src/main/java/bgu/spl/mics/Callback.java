package bgu.spl.mics;

/**
 * a callback is a function designed to be called when a message is received.
 */
public interface Callback<T> {

    public void call(T c);

}
