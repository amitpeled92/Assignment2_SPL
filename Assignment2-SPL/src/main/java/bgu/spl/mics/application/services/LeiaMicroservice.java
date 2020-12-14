package bgu.spl.mics.application.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import bgu.spl.mics.Event;
import bgu.spl.mics.Future;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.messages.FinishBroadcast;
import bgu.spl.mics.application.messages.GettingStartedEvent;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;

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
	private Queue<Future> qfuture;
	
    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
		this.attacks = attacks;
		qfuture= new LinkedList<>();
    }

    @Override
    protected void initialize() {
        this.subscribeEvent(GettingStartedEvent.class, c -> {
            for (int i=0;i<attacks.length;i++) {
                qfuture.add(this.sendEvent(new AttackEvent(attacks[i])));
            }
            boolean checkalldone=true;
            boolean endwait=true;
            synchronized (messageBus.getHashMapfuture()) {
                while (endwait) {
                    for (Future future : qfuture) {
                        if (!future.isDone()) {
                            checkalldone = false;
                            messageBus.getHashMapfuture().wait();
                        }
                    }
                    if (checkalldone) {
                        endwait = false;
                    } else {
                        checkalldone = true;
                    }
                }
            }
            this.sendEvent(new DeactivationEvent());
            this.complete(c,true);
        });
        this.subscribeBroadcast(FinishBroadcast.class, c -> {
            Diary.getInstance().setLeiaTerminate(System.currentTimeMillis());
            finishrun=true;
        });
        this.sendEvent(new GettingStartedEvent());
    }

}
