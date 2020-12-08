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
    private String output;

    /**
     * creating Diary as a thread safe singleton
     */
    private Diary(){
        output="";
    }

    public static Diary getInstance(){
        return DairySingletonHolder.diaryInstance;
    }

    /**
     * getter for output variable
     * @return output
     */
    public String getOutput(){
        return output;
    }

    /**
     * setter for output variable
     * @param str is a line which we want to add to Diary's output
     */
    public void setOutput(String str){
        output = output + "\nstr";
    }
}
