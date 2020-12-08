package bgu.spl.mics.application.services;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Event;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;

/**
 * R2D2Microservices is in charge of the handling {@link DeactivationEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link DeactivationEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class R2D2Microservice extends MicroService {

    public R2D2Microservice(long duration) {
        super("R2D2");
    }

    @Override
    protected void initialize() {
        this.subscribeEvent(DeactivationEvent.class, c -> {});
       // this.subscribeBroadcast(DeactivationEvent.class, c -> {});
    }

    @Override
    public void call(Object c) {
//        if(messageBus.hashmap.at(i).size() == 2) //r2d2 queue size = 2{
            //TODO: Adding "HanSolo and C3PO finished their tasks in {time} milliseconds one after the other. to Diary"
//            Event<Boolean> bdEvent = new BombDestroyerEvent();
//            messageBus.sendEvent(bdEvent);
//        }
    }
}
