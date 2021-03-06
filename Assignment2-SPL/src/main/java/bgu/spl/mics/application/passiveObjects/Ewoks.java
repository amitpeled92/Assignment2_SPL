package bgu.spl.mics.application.passiveObjects;


import java.util.ArrayList;

/**
 * Passive object representing the resource manager.
 * <p>
 * This class must be implemented as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private methods and fields to this class.
 */
public class Ewoks {
    private static class EwoksSingletonHolder {
        //this is thread-safe singleton
        private static Ewoks ewoksInstance = new Ewoks();
    }
    private static Ewok[] ewoksArr;


    private Ewoks(){
    }

    /**
     * initializing the Ewoks array with Ewok objects
     */
    public void init(int num)
    {
        ewoksArr= new Ewok[num];
        for (int i=0;i<num;i++)
        {
            ewoksArr[i]= new Ewok();
        }
    }

    /**
     * creating Ewoks as a thread safe singleton
     */
    public static Ewoks getInstance(){
        return EwoksSingletonHolder.ewoksInstance;
    }

    public static Ewok[] getEwoksArr() {
        return ewoksArr;
    }
}
