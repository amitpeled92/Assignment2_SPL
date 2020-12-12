package bgu.spl.mics.application.passiveObjects;


/**
 * Passive data-object representing a Diary - in which the flow of the battle is recorded.
 * We are going to compare your recordings with the expected recordings, and make sure that your output makes sense.
 * <p>
 * Do not add to this class nothing but a single constructor, getters and setters.
 */
public class Diary {
    private static class DairySingletonHolder {
        //this is thread-safe singleton
        private static Diary diaryInstance = new Diary();
    }
    private int totalAttacks;
    private long HanSoloFinish;
    private long C3POFinish;
    private long R2D2Deactivate;
    private long LeiaTerminate;
    private long HanSoloTerminate;
    private long C3POTerminate;
    private long R2D2Terminate;
    private long LandoTerminate;

    public void setTotalAttacks(int totalAttacks) {
        this.totalAttacks = totalAttacks;
    }

    public void setHanSoloFinish(long hanSoloFinish) {
        HanSoloFinish = hanSoloFinish;
    }

    public void setC3POFinish(long c3POFinish) {
        C3POFinish = c3POFinish;
    }

    public void setR2D2Deactivate(long r2D2Deactivate) {
        R2D2Deactivate = r2D2Deactivate;
    }

    public void setLeiaTerminate(long leiaTerminate) {
        LeiaTerminate = leiaTerminate;
    }

    public void setHanSoloTerminate(long hanSoloTerminate) {
        HanSoloTerminate = hanSoloTerminate;
    }

    public void setC3POTerminate(long c3POTerminate) {
        C3POTerminate = c3POTerminate;
    }

    public void setR2D2Terminate(long r2D2Terminate) {
        R2D2Terminate = r2D2Terminate;
    }

    public void setLandoTerminate(long landoTerminate) {
        LandoTerminate = landoTerminate;
    }

    public int getTotalAttacks() {
        return totalAttacks;
    }

    /**
     * creating Diary as a thread safe singleton
     */
    private Diary(){}

    public static Diary getInstance(){
        return DairySingletonHolder.diaryInstance;
    }

    public String toString(){
        String str="";
        str += "totalAttacks: " + totalAttacks + "\n" + "hanSoloFinish: " + HanSoloFinish + "\n" + "c3poFinish: " + C3POFinish + "\n" +
                "R2D2Deactivate: " + R2D2Deactivate + "\n" + "LieaTerminate: " + LeiaTerminate + "\n" + "HanSoloTerminate: " + HanSoloTerminate + "\n" +
                "C3POTerminate: " + C3POTerminate + "\n" + "R2D2Terminate: " + R2D2Terminate + "\n" + "LandoTerminate: " + LandoTerminate;
        return str;
    }
}
