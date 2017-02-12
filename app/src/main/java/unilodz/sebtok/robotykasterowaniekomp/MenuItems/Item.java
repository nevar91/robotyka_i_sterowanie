package unilodz.sebtok.robotykasterowaniekomp.MenuItems;

/**
 * Created by tokes on 07.01.2017.
 */

public class Item {

    private String name;
    private int thumbnail;

    public Item() {
    }

    public Item(String name, int thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

}
