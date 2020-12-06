package bgu.spl.mics.application.services;


import bgu.spl.mics.Event;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;

/**
 * HanSoloMicroservices is in charge of the handling {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class HanSoloMicroservice extends MicroService {

    public HanSoloMicroservice() {
        super("Han");
    }


    @Override
    protected void initialize() {
        this.subscribeEvent(AttackEvent.class, c -> {});
        this.subscribeBroadcast(AttackEvent.class, c -> {});
    }

    @Override
    public void call(Object c) {
//        if(messageBus.hashmap.at(i).isEmpty()) {
//            Event<Boolean> d1Event = new DeactivationEvent();
//            messageBus.sendEvent(d1Event);
//        }
    }
}
