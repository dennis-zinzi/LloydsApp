package uk.ac.ncl.csc2022.team10.datatypes;

/**
 * Created by Dennis on 15/4/15.
 */
public class TimeCounter {
    private long startTime;

    public TimeCounter(){
        startTime = System.currentTimeMillis();
    }

    public void resetTimer(){
        startTime = System.currentTimeMillis();
    }

    public long countTime(){
        return System.currentTimeMillis()-startTime;
    }
}
