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
	private static class MessageBusSingletonHolder{
		//this is thread-safe singleton
		private static MessageBusImpl messageBusInstance = new MessageBusImpl();
	}
	private HashMap<MicroService,Queue<Message>> hashMap;

	private MessageBusImpl(){

	}

	public static MessageBusImpl getInstance(){
		return MessageBusSingletonHolder.messageBusInstance;
	}
	
	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		
    }

	@Override @SuppressWarnings("unchecked")
	public <T> void complete(Event<T> e, T result) {
		
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		
        return null;
	}

	@Override
	public void register(MicroService m) {
		if(hashMap == null)
			hashMap = new HashMap<MicroService,Queue<Message>>();
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
