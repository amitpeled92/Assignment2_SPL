package bgu.spl.mics.application.services;

import bgu.spl.mics.Event;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.Main;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.FinishBroadcast;
import bgu.spl.mics.application.messages.FinishEvent;
import bgu.spl.mics.application.passiveObjects.Diary;

/**
 * LandoMicroservice
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LandoMicroservice  extends MicroService {
    private long dur;

    public LandoMicroservice(long duration) {
        super("Lando");
        dur= duration;
    }

    @Override
    protected void initialize() {
        this.subscribeEvent(BombDestroyerEvent.class, c -> {
            Thread.currentThread().sleep(dur);
            this.sendBroadcast(new FinishBroadcast());
            this.complete(c,true);
            //this.notifyall;
        });
        this.subscribeBroadcast(FinishBroadcast.class, c -> {
            Diary.getInstance().setLandoTerminate(System.currentTimeMillis());
            finishrun=true;
            Main.end=true;
        });
    }
}
