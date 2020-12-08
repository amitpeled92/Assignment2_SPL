package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.services.C3POMicroservice;
import bgu.spl.mics.application.services.HanSoloMicroservice;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

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
	private ConcurrentHashMap<MicroService,Queue<Message>> hashMap;
	private String messageLoopMannerName;

	private MessageBusImpl(){
		hashMap = new ConcurrentHashMap<MicroService,Queue<Message>>();
		messageLoopMannerName = null;
	}

	public static MessageBusImpl getInstance(){
		return MessageBusSingletonHolder.messageBusInstance;
	}

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		Queue<? extends type> q = new LinkedList<>();
		LinkedList<? extends Message> q = new LinkedList<>();
		hashMap.put(m,q);
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		Queue<type> q= new LinkedList<>();
		hashMap.put(m, q);
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
			if(q.getClass() == b.getClass())
			{
				q.add(b);
			}
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		Future<T> future= new Future<T>();

		for (MicroService m:hashMap.keySet())
		{

			if(hashMap.get(m).getClass() == e.getClass())
			{
				if (e.getClass()== AttackEvent.class)
				{
					String str= messageLoopMannerCheck();
					if (str.equals(m.getName()))
					{
						hashMap.get(m).add(e);
					}
				}
				else {
					hashMap.get(m).add(e);
				}
			}
		}
		return future;
	}

	@Override
	public void register(MicroService m) {
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
		Queue<? extends Message> q= hashMap.get(m);
		while (q.isEmpty())
		{
			try {
				wait();
			}
			catch (InterruptedException e)
			{

			}
		}
		Message message=q.poll();
		notifyAll();
		return message;
	}
	public String messageLoopMannerCheck(){
		boolean solo= false;
		boolean c3=false;
		for (MicroService m: hashMap.keySet()) {
			if (m.getName().equals("HanSolo")) {
				solo= true;
			}
			if(m.getName().equals("C3PO"))
			{
				c3= true;
			}
		}
		if(solo && c3) {
			if (messageLoopMannerName == null) {
				messageLoopMannerName = "HanSolo";
				return messageLoopMannerName; //HanSolo will handle the event this time
			} else if (messageLoopMannerName.equals("HanSolo")) {
				messageLoopMannerName = "C3PO";
				return messageLoopMannerName; //C3PO will handle the event this time
			} else {
				messageLoopMannerName = "HanSolo";
				return messageLoopMannerName; //HanSolo will handle the event this time
			}
		}
		else if(solo)
		{
			messageLoopMannerName = "HanSolo";
			return messageLoopMannerName; //HanSolo will handle the event this time
		}
		else if(c3)
		{
			messageLoopMannerName = "C3PO";
			return messageLoopMannerName; //C3PO will handle the event this time
		}
		else
			return null;
	}
}
