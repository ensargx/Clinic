import java.io.Serializable;
import java.util.LinkedList;

public class Section implements Serializable {
    private final int id;
    private String name;
    private LinkedList<Doctor> doctors = new LinkedList<>();

    public Section(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void listDoctors() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Doctor getDoctor(int diploma_id) {
        for ( Doctor doctor : doctors ) {
            if ( doctor.getDiplomaId() == diploma_id ) {
                return doctor;
            }
        }

        return null;
    }

    public void addDoctor(Doctor doctor) throws DuplicateInfoException {
        for ( Doctor doc : doctors ) {
            if ( doc == doctor ) {
                throw new DuplicateInfoException("The doctor allready exists!");
            }
        }

        doctors.add(doctor);
    }
}
