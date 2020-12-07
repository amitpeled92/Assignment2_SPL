package bgu.spl.mics;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
	private static MessageBusImpl messageBusInstance = null;
	private HashMap<MicroService,Queue<?extends Message>> hashMap;

	private MessageBusImpl(){

	}

	public static MessageBusImpl getInstance(){
		if(messageBusInstance == null)
			messageBusInstance = new MessageBusImpl();
		return messageBusInstance;
	}
	
	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		Queue<type> q= new LinkedList<>();
		hashMap.put(m, (Queue<? extends Message>) q);
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		Queue<type> q= new LinkedList<>();
		hashMap.put(m, (Queue<? extends Message>) q);
    }

	@Override @SuppressWarnings("unchecked")
	public <T> void complete(Event<T> e, T result) {
		Future<T> future= new Future<T>();
		future.resolve(result);
		//need to connect e with future

	}

	@Override
	public void sendBroadcast(Broadcast b) {
		for (Queue<? extends Message> q:hashMap.values())
		{
			if(q.getClass()==b.getClass())
			{
				q.add(b);
			}
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		Future<T> future= new Future<T>();
		for (Queue<? extends Message> q:hashMap.values())
		{
			if(q.getClass()==e.getClass())
			{
				q.add(e);
			}
		}
		return future;
	}

	@Override
	public void register(MicroService m) {
		if(hashMap == null)
			hashMap = new HashMap<MicroService,Queue<?extends Message>>();
		Queue<Message> qOfMicroservice = new LinkedList<>();
		hashMap.put(m,qOfMicroservice);
	}

	@Override
	public void unregister(MicroService m) {
		if(hashMap.containsKey(m)){
			hashMap.remove(m);
		}
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		
		return null;
	}
}
