import java.util.Comparator;

public class StudentComparator implements Comparator<Student> {

    @Override
    public int compare(Student o1, Student o2) {
        int comp = o1.getName().compareTo(o2.getName());
        return comp == 0 ? o1.getDateOfBirth().compareTo(o2.getDateOfBirth()) : comp;
    }
}
