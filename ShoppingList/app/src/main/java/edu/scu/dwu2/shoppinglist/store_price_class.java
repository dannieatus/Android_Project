package edu.scu.dwu2.shoppinglist;

/**
 * Created by Danni on 2/27/16.
 */
public class store_price_class {

    private String name;
    private String filename;

    public store_price_class(String name, String filename) {
        this.name = name;
        this.filename = filename;
    }
    public String getName() {
        return name;
    }
    public String getfileName() {
        return filename;
    }
}
