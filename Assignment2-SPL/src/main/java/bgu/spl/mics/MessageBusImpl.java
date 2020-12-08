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
	private String messageLoopMannerName;

	private MessageBusImpl(){
		hashMap = new HashMap<>();
		messageLoopMannerName=null;
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
		boolean check= true;
		for (Queue<? extends Message> q:hashMap.values())
		{
			if(q.getClass()==b.getClass())
			{
				check= false;
				q.add(b);
			}
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		Future<T> future= new Future<T>();
		boolean check= true;
		int events=-1;
		Queue<? extends Message> currq= null;
		for (Queue<? extends Message> q:hashMap.values())
		{
			if(q.getClass()==e.getClass()&&check)
			{
				//not robin manner temp action
				if(events==-1||events>q.size()) {
					events= q.size();
					currq=q;
				}
			}
		}
		currq.add(e);
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
		if(messageLoopMannerName == null){
			messageLoopMannerName = "HanSolo";
			return messageLoopMannerName; //HanSolo will handle the event this time
		}
		else if(messageLoopMannerName.equals("HanSolo")){
			messageLoopMannerName = "C3PO";
			return messageLoopMannerName; //C3PO will handle the event this time
		}
		else{
			messageLoopMannerName = "HanSolo";
			return messageLoopMannerName; //HanSolo will handle the event this time
		}
	}
}
