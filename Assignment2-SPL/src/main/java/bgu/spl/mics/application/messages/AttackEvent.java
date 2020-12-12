package bgu.spl.mics.application.messages;
import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Attack;

public class AttackEvent implements Event<Boolean> {
    public AttackEvent(Attack a)
    {
        this.attack=a;
    }
	private Attack attack;

    /** this method is returning the value of attack variable
     * @return attack
     */
    public Attack getAttack() {
        return attack;
    }

    /**
     * this method setting the attack variable
     * @param attack
     */
    public void setAttack(Attack attack) {
        this.attack = attack;
    }
}
