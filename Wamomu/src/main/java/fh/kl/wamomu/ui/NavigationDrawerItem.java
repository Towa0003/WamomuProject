package fh.kl.wamomu.ui;

/**
 * Created by Christian on 21.12.13.
 */
public class NavigationDrawerItem {
    private String title;
    private int icon;
    private String count = "0";
    // boolean to set visiblity of the counter
    private boolean isCounterVisible = false;



    public NavigationDrawerItem(String title, int icon){
        this.title = title;
        this.icon = icon;
    }



    public String getTitle(){
        return this.title;
    }

    public int getIcon(){
        return this.icon;
    }

    public String getCount(){
        return this.count;
    }

    public boolean getCounterVisibility(){
        return this.isCounterVisible;
    }

}
