package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.FinishBroadcast;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.services.HanSoloMicroservice;
import bgu.spl.mics.application.services.LeiaMicroservice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.table.AbstractTableModel;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessageBusImplTest {

    private MessageBusImpl msgBus;
    private Event<Boolean> attackEventCheck;
    //MicroServices:
    private HanSoloMicroservice hanSolo;
    private LeiaMicroservice leia;

    @BeforeEach
    void setUp() {
        msgBus = MessageBusImpl.getInstance();
        List<Integer> serialNumbers= new LinkedList<>();
        serialNumbers.add(1);
        int duration=1;
        Attack a= new Attack(serialNumbers,duration);
        attackEventCheck = new AttackEvent(a);
        //MicroServices:
        hanSolo = new HanSoloMicroservice();
        Attack[] attacks = new Attack[1];
        leia = new LeiaMicroservice(attacks);
    }

    @AfterEach
    void tearDown() {
        msgBus.unregister(hanSolo);
        msgBus.unregister(leia);
    }

    @Test
    void subscribeEvent() {
        msgBus.register(hanSolo);
        msgBus.subscribeEvent(AttackEvent.class, hanSolo);
        assertTrue(msgBus.getHashMapofmicroservices().get(AttackEvent.class).contains(hanSolo));
    }

    @Test
    void subscribeBroadcast() {
        //hanSolo = new HanSoloMicroservice();
        msgBus.register(hanSolo);
        msgBus.subscribeBroadcast(FinishBroadcast.class, hanSolo);
        assertTrue(msgBus.getHashMapofmicroservices().get(FinishBroadcast.class).contains(hanSolo));
    }

    @Test
    void complete() {
        msgBus.register(hanSolo);
        msgBus.subscribeEvent(AttackEvent.class, hanSolo);
        Future<Boolean> f = msgBus.sendEvent(attackEventCheck);
        msgBus.complete(attackEventCheck, true);
        assertTrue(f.get());
        msgBus.complete(attackEventCheck, false);
        assertFalse(f.get());
    }

    @Test
    void sendBroadcast() {
        Broadcast attackEventCheckb = new FinishBroadcast();
        msgBus.register(hanSolo);
        msgBus.register(leia);
        msgBus.subscribeBroadcast(FinishBroadcast.class,hanSolo);
        msgBus.subscribeBroadcast(FinishBroadcast.class,leia);
        msgBus.sendBroadcast(attackEventCheckb);
        try {
            FinishBroadcast attackEventHan = (FinishBroadcast) msgBus.awaitMessage(hanSolo);
            assertEquals(attackEventCheckb, attackEventHan);
            FinishBroadcast attackEventLeia = (FinishBroadcast) msgBus.awaitMessage(leia);
            assertEquals(attackEventCheckb, attackEventLeia);
        }
        catch (Exception e)
        {
            fail();
        }
    }

    @Test
    void sendEvent() {
        List<Integer> serialNumbers= new LinkedList<>();
        serialNumbers.add(1);
        int duration=1;
        Attack a2= new Attack(serialNumbers,duration);
        AttackEvent attackEventCheck2= new AttackEvent(a2);
        msgBus.register(hanSolo);
        msgBus.register(leia);
        msgBus.subscribeEvent(AttackEvent.class,hanSolo);
        msgBus.subscribeEvent(AttackEvent.class,leia);
        msgBus.sendEvent(attackEventCheck);
        msgBus.sendEvent(attackEventCheck2);
        try {
            AttackEvent attackEventHan = (AttackEvent) msgBus.awaitMessage(hanSolo);
            assertEquals(attackEventCheck, attackEventHan);
            AttackEvent attackEventLeia = (AttackEvent) msgBus.awaitMessage(leia);
            assertEquals(attackEventCheck2, attackEventLeia);
        }
        catch (Exception e)
        {
            fail();
        }
    }

    @Test
    void register() {
        msgBus.register(hanSolo);
        assertTrue(msgBus.getHashMapmessages().containsKey(hanSolo));
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
        msgBus.register(hanSolo);
        msgBus.subscribeEvent(AttackEvent.class,hanSolo);
        msgBus.sendEvent(attackEventCheck);
        try {
            AttackEvent at = (AttackEvent) msgBus.awaitMessage(hanSolo);
            assertEquals(attackEventCheck, at);
        }
        catch (Exception e){
            //throw new Exception(e.Message +" , the event was interrupted");
            fail();
        }
    }
}