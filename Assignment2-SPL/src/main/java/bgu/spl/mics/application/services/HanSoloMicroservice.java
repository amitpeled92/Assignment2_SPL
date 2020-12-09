package bgu.spl.mics.application.services;


import bgu.spl.mics.Callback;
import bgu.spl.mics.Event;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;

import java.util.LinkedList;
import java.util.Queue;

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
    private Queue<Callback<AttackEvent>> hanSoloCallbackQueue = null;

    @Override
    protected void initialize() {
        this.subscribeEvent(AttackEvent.class, (AttackEvent ae) -> {

            if(hanSoloCallbackQueue==null){
                hanSoloCallbackQueue = new LinkedList<>();
            }

        });
        this.subscribeBroadcast(AttackEvent.class, c -> {});
    }
}
