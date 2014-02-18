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


    /**
     * Konstruktor für den NavigationDrawerItem
     * @param title
     * @param icon
     */
    public NavigationDrawerItem(String title, int icon){
        this.title = title;
        this.icon = icon;
    }


    /**
     * Gibt den Titel zurück
     * @return title
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Gibt den Icon zurück
     * @return icon
     */
    public int getIcon(){
        return this.icon;
    }

    /**
     * Gibt den Zähler zurück
     * @return count
     */
    public String getCount(){
        return this.count;
    }

    /**
     * Gibt zurück, ob das Element sichtbar ist oder nicht
     * @return isCountervisible
     */
    public boolean getCounterVisibility(){
        return this.isCounterVisible;
    }

}
