package wrapper;

/**
 * Created by Nouha on 07/03/2015.
 */
public class ListItemWrapper  {

    private int mImage;
    private String Name;
    private String LastName;

    public ListItemWrapper() {

    }

    public ListItemWrapper(int mImage,String Name,String LastName)
    {
        this.mImage = mImage;
        this.Name = Name;
        this.LastName = LastName;
    }
    public int getItemImage()
    {
        return mImage;
    }
    public void setItemImage(int mImage)
    {
        this.mImage = mImage;
    }
    public String getName()
    {
        return Name;
    }
    public void setName(String Name)
    {
        this.Name = Name;
    }
    public String getLastName()
    {
        return LastName;
    }
    public void setLastName(String LastName)
    {
        this.LastName = LastName;
    }

}
