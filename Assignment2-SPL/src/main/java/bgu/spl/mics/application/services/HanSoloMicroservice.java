package bgu.spl.mics.application.services;


import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.FinishBroadcast;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewoks;

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
        this.subscribeEvent(AttackEvent.class, c -> {
            Ewoks ewoks= Ewoks.getInstance();
            Attack attack=c.getAttack();
            synchronized (ewoks) {
                boolean endwait = true;
                boolean checkavailable = true;
                while (endwait) {
                    for (Integer integer : attack.getSerials()) {
                        if (!Ewoks.getEwoksArr()[integer - 1].isAvailable()) {
                            try {
                                checkavailable = false;
                                ewoks.wait();
                            } catch (InterruptedException e) {
                            }
                        }
                    }
                    if (checkavailable) {
                        endwait = false;
                    } else {
                        checkavailable = true;
                    }
                }

                for (Integer integer : attack.getSerials()) {
                    Ewoks.getEwoksArr()[integer - 1].acquire();
                    ewoks.notifyAll();
                }
            }
            try {
                Thread.currentThread().sleep(attack.getDuration());
                synchronized (ewoks) {
                    for (Integer integer : attack.getSerials()) {
                        Ewoks.getEwoksArr()[integer - 1].release();
                        ewoks.notifyAll();
                    }
                }
                this.complete(c,true);
                Diary.getInstance().setTotalAttacks(Diary.getInstance().getTotalAttacks()+1);
                Diary.getInstance().setHanSoloFinish(System.currentTimeMillis());
            }
            catch (Exception e){}
        });

        this.subscribeBroadcast(FinishBroadcast.class, c -> {
            Diary.getInstance().setHanSoloTerminate(System.currentTimeMillis());
            finishrun=true;
        });

    }


}
