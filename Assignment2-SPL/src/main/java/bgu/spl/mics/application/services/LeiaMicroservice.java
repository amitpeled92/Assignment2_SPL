package bgu.spl.mics.application.services;

import java.util.ArrayList;
import java.util.List;

import bgu.spl.mics.Event;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.GettingStartedEvent;
import bgu.spl.mics.application.passiveObjects.Attack;

/**
 * LeiaMicroservices Initialized with Attack objects, and sends them as  {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LeiaMicroservice extends MicroService {
	private Attack[] attacks;
	
    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
		this.attacks = attacks;
    }

    @Override
    protected void initialize() {
        this.subscribeEvent(GettingStartedEvent.class, c -> {});
       // this.subscribeBroadcast(GettingStartedEvent.class, c -> {});
    }

    @Override
    public void call(Object c) {
//        if (messageBus.hashmap.at(i)!=null) //if JSON file was read and attack Events were created
//        {
//            for(int i=0; i < attacks.length; i++){
//            Event<Boolean> attackEvent = new AttackEvent();
//            messageBus.sendEvent(attackEvent);
//        }
        //TODO: Adding "There are n attacks. to Diary"
    }
}
