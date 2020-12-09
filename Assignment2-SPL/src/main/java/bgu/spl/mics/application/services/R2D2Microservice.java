package bgu.spl.mics.application.services;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Event;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.messages.FinishBroadcast;
import bgu.spl.mics.application.passiveObjects.Diary;

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
        dur= duration;
    }
    private long dur;
    @Override
    protected void initialize() {
        this.subscribeEvent(DeactivationEvent.class, c -> {
            try {
                Thread.currentThread().sleep(dur);
                this.sendEvent(new BombDestroyerEvent());
                messageBus.getHashMapmessages().notifyAll();
            }
            catch (Exception e){}
            this.complete(c,true);
            Diary.getInstance().setR2D2Deactivate(System.currentTimeMillis());
        });
        this.subscribeBroadcast(FinishBroadcast.class, c -> {
            Diary.getInstance().setR2D2Terminate(System.currentTimeMillis());
            finishrun=true;
        });
    }
}
