import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class CRS {
    private HashMap<Long, Patient> patients = new HashMap<>();
    private LinkedList<Rendezvous> rendezvous = new LinkedList<>();
    private HashMap<Integer, Hospital> hospitals = new HashMap<>();

    public boolean makeRendezvous(long patientId, long hospitalId, long SectionId, int diplomaId, Date desiredDate) {
        return false;
    }

    public void saveTablesToDisk(String fullPath) {

    }

    public void loadTablesFromDisc(String fullPath) {

    }
}
