package erpsystem;

/*package erpsystem;

/**
 *
 * @author Lupe
 */
public class Calendar {
    
    // vars for constructor
    private String date;
    private String event;
    private int duration;
    
    // creates calendar object
    public Calendar(String date, String event, int duration)
    {
        this.date = date;
        this.event = event;
        this.duration = duration;
    }
    
    // get methods
    public String getDate()
    {
        return this.date;
    }
    
    public String getEvent()
    {
        return this.event;
    }
    
    public int getDuration()
    {
        return this.duration;
    }
    
    // set methods
    public void setDate(String date)
    {
        this.date = date;
    }
    
    public void setEvent(String event)
    {
        this.event = event;
    }
    
    public void setDuration(int duration)
    {
        this.duration = duration;
    }
    
    // override build in toString for easy printing
    @Override
    public String toString()
    {
        return ("["+getDate()+"] "+getEvent()+" | Duration: "+getDuration()+" hours");
    }
    
}
