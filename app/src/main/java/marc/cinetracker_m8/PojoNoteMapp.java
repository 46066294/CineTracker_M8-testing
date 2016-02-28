package marc.cinetracker_m8;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by 46066294p on 24/02/16.
 */
public class PojoNoteMapp {

    private Map<Integer, PojoForNote> mapPojoForNote = new TreeMap<>();

    public PojoNoteMapp(){}

    public PojoNoteMapp(Map<Integer, PojoForNote> mapPojoForNote) {
        this.mapPojoForNote = mapPojoForNote;
    }

    //getters-setters

    public Map<Integer, PojoForNote> getMapPojoForNote() {
        return mapPojoForNote;
    }

    public void setMapPojoForNote(Map<Integer, PojoForNote> mapPojoForNote) {
        this.mapPojoForNote = mapPojoForNote;
    }


}
