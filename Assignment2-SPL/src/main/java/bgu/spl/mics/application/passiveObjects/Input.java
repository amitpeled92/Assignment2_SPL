package bgu.spl.mics.application.passiveObjects;

public class Input {
    private Attack[] attacks;
    long R2D2;
    long Lando;
    int Ewoks;

    /**
     * getters and setters for Input's variables
     */

    public int getEwoks() {
        return Ewoks;
    }
    public void setEwoks(int ewoks) {
        Ewoks = ewoks;
    }
    public long getLando() {
        return Lando;
    }
    public void setLando(long lando) {
        Lando = lando;
    }
    public long getR2D2() {
        return R2D2;
    }
    public void setR2D2(long r2d2) {
        R2D2 = r2d2;
    }
    public Attack[] getAttacks() {
        return this.attacks;
    }
    public void setAttacks(Attack[] attacks) {
        this.attacks = attacks;
    }
}
