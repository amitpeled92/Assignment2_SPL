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
    private static Ewoks ewoksInstance = null;
    private ArrayList<Ewok> ewoksList;

    private Ewoks(){
        //Ewoks going to be a list of every single ewok that the program including
        ewoksList=new ArrayList<>();
    }

    public static Ewoks getInstance(){
        if(ewoksInstance == null)
            ewoksInstance=new Ewoks();
        return ewoksInstance;
    }
}
