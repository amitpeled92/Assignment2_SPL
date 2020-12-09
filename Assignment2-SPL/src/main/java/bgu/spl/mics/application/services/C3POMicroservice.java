package bgu.spl.mics.application.services;

import bgu.spl.mics.Event;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewoks;


/**
 * C3POMicroservices is in charge of the handling {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class C3POMicroservice extends MicroService {
	
    public C3POMicroservice() {
        super("C3PO");
    }

    @Override
    protected void initialize() {
        this.subscribeEvent(AttackEvent.class, c -> {
            Ewoks ewoks= Ewoks.getInstance();
            Attack attack=c.getAttack();
            synchronized (ewoks) {
                boolean endwait = true;
                boolean checkavailable = true;
                while (endwait) {
                    for (Integer integer : attack.getSerials()) {
                        if (!Ewoks.getEwoksArr()[integer].isAvailable()) {
                            try {
                                checkavailable = false;
                                ewoks.wait();
                            } catch (InterruptedException e) {
                            }
                        }
                    }
                    if (checkavailable) {
                        endwait = false;
                    }
                }
                for (Integer integer : attack.getSerials()) {
                    Ewoks.getEwoksArr()[integer].acquire();
                }
                Thread.currentThread().notifyAll();
            }
            try {
                Thread.currentThread().sleep(attack.getDuration());
                for (Integer integer : attack.getSerials()) {
                    Ewoks.getEwoksArr()[integer].release();
                }
                this.complete(c,true);
                Diary.getInstance().setTotalAttacks(Diary.getInstance().getTotalAttacks()+1);
                Thread.currentThread().notifyAll();
            }
            catch (Exception e){}
        });
        this.subscribeBroadcast(StopSendAttacksBroadcast.class, c -> {
            Diary.getInstance().setC3POFinish(System.currentTimeMillis());
        });
        this.subscribeBroadcast(FinishBroadcast.class, c -> {
            Diary.getInstance().setC3POTerminate(System.currentTimeMillis());
            finishrun=true;
        });
    }

    @Override
    public void call(Object c) {
//        if(messageBus.hashmap.at(i).isEmpty()) {
//            Event<Boolean> d2Event = new DeactivationEvent();
//            messageBus.sendEvent(d2Event);
//        }
    }
}
