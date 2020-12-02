package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.services.HanSoloMicroservice;
import bgu.spl.mics.application.services.LeiaMicroservice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageBusImplTest {

    private MessageBusImpl msgBus;
    private Event<Boolean> attackEventCheck;
    //MicroServices:
    private HanSoloMicroservice hanSolo;
    private LeiaMicroservice leia;

    @BeforeEach
    void setUp() {
        msgBus = new MessageBusImpl();
        attackEventCheck = new AttackEvent();
        //MicroServices:
        hanSolo = new HanSoloMicroservice();
        Attack[] attacks = new Attack[1];
        leia = new LeiaMicroservice(attacks);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void subscribeEvent() {
//        hanSolo = new HanSoloMicroservice();
//        attackEventCheck = new AttackEvent();
        msgBus.register(hanSolo);
        msgBus.subscribeEvent(AttackEvent.class, hanSolo);
        MicroService m = msgBus.microServiceQueue.peek();
        assertEquals(m,hanSolo);
    }

    @Test
    void subscribeBroadcast() {
        //hanSolo = new HanSoloMicroservice();
        msgBus.register(hanSolo);
        msgBus.subscribeBroadcast(AttackEvent.class, hanSolo);
        MicroService m = msgBus.microServiceQueue.peek();
        assertEquals(m,hanSolo);
    }

    @Test
    void complete() {
        msgBus.register(hanSolo);
        msgBus.subscribeEvent(AttackEvent.class, hanSolo);
        Future<Boolean> f = msgBus.sendEvent(attackEventCheck);
        try {
            msgBus.awaitMessage(hanSolo);
            msgBus.complete(attackEventCheck, true);
            //f.resolve(true);
            assertTrue(f.get());
        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    void sendBroadcast() {
        //hanSolo = new HanSoloMicroservice();
        Broadcast attackEventCheckb = new AttackEvent();
        msgBus.register(hanSolo);
        msgBus.subscribeBroadcast(AttackEvent.class,hanSolo);
        msgBus.sendBroadcast(/*(Broadcast)*/ attackEventCheckb);
        try {
            AttackEvent attackEventNew = (AttackEvent) msgBus.awaitMessage(hanSolo);
            assertEquals(attackEventCheckb, attackEventNew);
        }
        catch (Exception e)
        {
            fail();
        }
    }

    @Test
    void sendEvent() {
//        attackEventCheck = new AttackEvent();
//        hanSolo = new HanSoloMicroservice();
        msgBus.register(hanSolo);
        msgBus.subscribeEvent(AttackEvent.class,hanSolo);
        msgBus.sendEvent(attackEventCheck);

        try {
            AttackEvent attackEventNew = (AttackEvent) msgBus.awaitMessage(hanSolo);
            assertEquals(attackEventCheck, attackEventNew);
        }
        catch (Exception e)
        {
            fail();
        }
    }

    @Test
    void register() {
        //hanSolo = new HanSoloMicroservice();
        msgBus.register(hanSolo);
        assertEquals(msgBus.microServiceQueue.peek(), hanSolo);
    }

    @Test
    void unregister() {
        /*
         * No need to create test
         */
    }

    /*
     * testing with the assumption that the queue is not empty
     */
    @Test
    void awaitMessage() {
        //hanSolo = new HanSoloMicroservice();
        AttackEvent at = null;
        msgBus.register(hanSolo);
        //attackEventCheck = new AttackEvent();
        msgBus.sendEvent(attackEventCheck);
        try {
            at = (AttackEvent) msgBus.awaitMessage(hanSolo);
        }
        catch (Exception e){
            //throw new Exception(e.Message +" , the event was interrupted");
            fail();
        }
        assertEquals(attackEventCheck, at);
    }
}