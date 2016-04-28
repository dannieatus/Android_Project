package edu.scu.dwu2.zoodirectory;

/**
 * Created by Danni on 1/28/16.
 */
public class meet_animals_list {
    private String name;
    private String filename;

    public meet_animals_list(String name, String filename) {
        this.name = name;
        this.filename = filename;
    }

    public String getName() {
        return name;
    }

    public String getFilename() {
        return filename;
    }
}