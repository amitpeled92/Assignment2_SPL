package bgu.spl.mics;

import bgu.spl.mics.application.messages.FinishBroadcast;
import bgu.spl.mics.application.messages.FinishEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * The MicroService is an abstract class that any micro-service in the system
 * must extend. The abstract MicroService class is responsible to get and
 * manipulate the singleton {@link MessageBus} instance.
 * <p>
 * Derived classes of MicroService should never directly touch the message-bus.
 * Instead, they have a set of internal protected wrapping methods (e.g.,
 * {@link #sendBroadcast(bgu.spl.mics.Broadcast)}, {@link #sendBroadcast(bgu.spl.mics.Broadcast)},
 * etc.) they can use. When subscribing to message-types,
 * the derived class also supplies a {@link Callback} that should be called when
 * a message of the subscribed type was taken from the micro-service
 * message-queue (see {@link MessageBus#register(bgu.spl.mics.MicroService)}
 * method). The abstract MicroService stores this callback together with the
 * type of the message is related to.
 * 
 * Only private fields and methods may be added to this class.
 * <p>
 */
public abstract class MicroService implements Runnable, Callback {

    protected MessageBusImpl messageBus;
    private String msName;
    protected HashMap<Class,Callback> callbackHashMap;
    protected boolean finishrun;

    /**
     * @param name the micro-service name (used mainly for debugging purposes -
     *             does not have to be unique)
     */
    public MicroService(String name) {
        messageBus = MessageBusImpl.getInstance();
        msName = name;
        callbackHashMap= new HashMap<>();
        finishrun=false;
       // callbackQueue = new LinkedList<>();
        //initialize();
    }

    /**
     * Subscribes to events of type {@code type} with the callback
     * {@code callback}. This means two things:
     * 1. Subscribe to events in the singleton event-bus using the supplied
     * {@code type}
     * 2. Store the {@code callback} so that when events of type {@code type}
     * are received it will be called.
     * <p>
     * For a received message {@code m} of type {@code type = m.getClass()}
     * calling the callback {@code callback} means running the method
     * {@link Callback#call(java.lang.Object)} by calling
     * {@code callback.call(m)}.
     * <p>
     * @param <E>      The type of event to subscribe to.
     * @param <T>      The type of result expected for the subscribed event.
     * @param type     The {@link Class} representing the type of event to
     *                 subscribe to.
     * @param callback The callback that should be called when messages of type
     *                 {@code type} are taken from this micro-service message
     *                 queue.
     */
    protected final <T, E extends Event<T>> void subscribeEvent(Class<E> type, Callback<E> callback) {
//      Queue<E> q = new PriorityBlockingQueue<>();
//    	messageBus.hashmap.at(i).add(q);
//    	messageBus.hashmap.at(i).add(callback);

        messageBus.subscribeEvent(type, this);
        callbackHashMap.put(type,callback);
        //callback.call();
    }

    /**
     * Subscribes to broadcast message of type {@code type} with the callback
     * {@code callback}. This means two things:
     * 1. Subscribe to broadcast messages in the singleton event-bus using the
     * supplied {@code type}
     * 2. Store the {@code callback} so that when broadcast messages of type
     * {@code type} received it will be called.
     * <p>
     * For a received message {@code m} of type {@code type = m.getClass()}
     * calling the callback {@code callback} means running the method
     * {@link Callback#call(java.lang.Object)} by calling
     * {@code callback.call(m)}.
     * <p>
     * @param <B>      The type of broadcast message to subscribe to
     * @param type     The {@link Class} representing the type of broadcast
     *                 message to subscribe to.
     * @param callback The callback that should be called when messages of type
     *                 {@code type} are taken from this micro-service message
     *                 queue.
     */
    protected final <B extends Broadcast> void subscribeBroadcast(Class<B> type, Callback<B> callback) {
//        Queue<B> q = new PriorityBlockingQueue<>();
//        messageBus.hashmap.at(i).add(q);
//        messageBus.hashmap.at(i).add(callback);

        messageBus.subscribeBroadcast(type,this);
        callbackHashMap.put(type,callback);
        //callback.call();
    }

    /**
     * Sends the event {@code e} using the message-bus and receive a {@link Future<T>}
     * object that may be resolved to hold a result. This method must be Non-Blocking since
     * there may be events which do not require any response and resolving.
     * <p>
     * @param <T>       The type of the expected result of the request
     *                  {@code e}
     * @param e         The event to send
     * @return  		{@link Future<T>} object that may be resolved later by a different
     *         			micro-service processing this event.
     * 	       			null in case no micro-service has subscribed to {@code e.getClass()}.
     */
    protected final <T> Future<T> sendEvent(Event<T> e) {
    	Future<T>f= messageBus.sendEvent(e);
    	//call(new Object());

//    	for(Queue q:hashmap){
//    	    if(q.getClass() == e.getClass()){
//    	        Future<T> f = new Future<>();
//    	        return f;
//            }
//        }
        return f;
    }

    /**
     * A Micro-Service calls this method in order to send the broadcast message {@code b} using the message-bus
     * to all the services subscribed to it.
     * <p>
     * @param b The broadcast message to send
     */
    protected final void sendBroadcast(Broadcast b) {
        messageBus.sendBroadcast(b);
        //call(new Object());

//        for(Queue q:hashmap){
//            if(q.getClass() == b.getClass()){
//                Future<Boolean> f = new Future<>();
//            }
//        }
    }

    /**
     * Completes the received request {@code e} with the result {@code result}
     * using the message-bus.
     * <p>
     * @param <T>    The type of the expected result of the processed event
     *               {@code e}.
     * @param e      The event to complete.
     * @param result The result to resolve the relevant Future object.
     *               {@code e}.
     */
    protected final <T> void complete(Event<T> e, T result) {
    	messageBus.complete(e, result);
        //call(new Object());
    }

    /**
     * this method is called once when the event loop starts.
     */
    protected abstract void initialize();

    /**
     * Signals the event loop that it must terminate after handling the current
     * message.
     */
    protected final void terminate() {
        messageBus.sendBroadcast(new FinishBroadcast());
    }

    /**
     * @return the name of the service - the service name is given to it in the
     *         construction time and is used mainly for debugging purposes.
     */
    public final String getName() {
        return msName;
    }

    /**
     * The entry point of the micro-service. TODO: you must complete this code
     * otherwise you will end up in an infinite loop.
     */
    @Override
    public final void run() {
        messageBus.register(this);
        initialize();
        while (!finishrun)
        try
        {
            Message m= messageBus.awaitMessage(this);
            callbackHashMap.get(m).call(m);
        }
        catch (InterruptedException ignored) {}
        messageBus.unregister(this);
    }

    /**
     * this method called once a microservice needs to create a callback to an event
     * @param c is the event/message that the microservice works with to create the callback
     */
    public abstract void call(Object c);

    /**
     * in our program there are only 2 microservices the can get the same type of events - HanSolo & C3PO.
     * therefore, every time this method called, it checks which of these 2 microservice will get the current event
     * to handle at that point of time.
     * @return a String of the name of the microservice that the event will be added to its queue
     */

}
