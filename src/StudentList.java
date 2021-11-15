import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class StudentList implements List<Student> {
    private int size = 0;
    private Student head;
    private Student tail;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        Student el = head;
        while(el != null) {
            if(el.equals(o))
                return true;
            el = el.getNext();
        }
        return false;
    }

    @Override
    public Iterator<Student> iterator() {
        return new Iterator<Student>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public Student next() {
                return get(index++);
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] o = new Object[size];
        int i = 0;
        Student el = head;
        while (el != null){
            o[i++] = el;
            el = el.getNext();
        }
        return o;
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        if(ts.length < size)
            ts = (T[])java.lang.reflect.Array.newInstance(
                    ts.getClass().getComponentType(), size);
        int i = 0;
        Object[] a = ts;
        Student el = head;
        while (el != null){
            a[i++] = new Student(el.getName(), el.getDateOfBirth(), el.getDetails());
            el = el.getNext();
        }
        if(ts.length > size){
            a[size] = null;
        }
        return (T[]) a;
    }

    @Override
    public boolean add(Student student) {
        Student el = new Student(student.getName(), student.getDateOfBirth(), student.getDetails());
        el.setPrev(tail);
        if(tail == null){
            head = el;
        } else {
            tail.setNext(el);
        }
        tail = el;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Student el = head;
        while (el != null){
            if(el.equals(o)){
                Student prev = el.getPrev();
                Student next = el.getNext();
                Student s = (Student) o;
                if(prev != null) {
                    prev.setNext(next);
                    s.setPrev(null);
                } else{
                   head = next;
                }
                if(next != null) {
                    next.setPrev(prev);
                    s.setNext(null);
                } else {
                    tail = prev;
                }
                deleteData(s);
                size--;
                return true;
            }
            el = el.getNext();
        }
        return false;
    }

    @Override
    public void clear() {
        Student el = head;
        while (el != null){
            Student next = el.getNext();
            deleteData(el);
            el.setNext(null); el.setPrev(null);
            el = next;
        }
        head = null; tail = null;
        size = 0;
    }

    @Override
    public Student get(int i) {
        checkIndex(i);
        int k = 0;
        Student el = head;
        while (el != null && k < i){
            el = el.getNext(); k++;
        }
        return (k == i) ? el : null;
    }

    @Override
    public Student set(int i, Student student) {
        System.out.println("here");
        checkIndex(i);
        Student updateStudent = get(i);
        Student oldStudent = new Student(updateStudent.getName(), updateStudent.getDateOfBirth(),
                updateStudent.getDetails(), updateStudent.getPrev(), updateStudent.getNext());
        updateStudent.setName(student.getName());
        updateStudent.setDateOfBirth(student.getDateOfBirth());
        updateStudent.setDetails(student.getDetails());
        return oldStudent;
    }

    @Override
    public void add(int i, Student student) {
        if(i == size) {
            add(student);
            return;
        }
        else
            checkIndex(i);
        Student succ = get(i);
        Student pred = succ.getPrev();
        student.setNext(succ);
        student.setPrev(pred);
        succ.setPrev(student);
        if(pred == null){
            head = student;
        } else {
            pred.setNext(student);
        }
        size++;
    }

    @Override
    public Student remove(int i) {
        checkIndex(i);
        Student student = get(i);
        remove(student);
        return student;
    }

    @Override
    public int indexOf(Object o) {
        Student el = head;
        int index = 0;
        while(el != null){
            if(el.equals(o)){
                return index;
            }
            el = el.getNext(); index++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Student el = tail;
        int index = size - 1;
        while (el != null){
            if(el.equals(o)){
                return index;
            }
            el = el.getPrev(); index--;
        }
        return -1;
    }

    @Override
    public ListIterator<Student> listIterator() {
        return new ListIterator<Student>() {
            private int fIndex = 0;
            private int bIndex = size - 1;

            @Override
            public boolean hasNext() {
                return fIndex < size;
            }

            @Override
            public Student next() {
                return get(fIndex++);
            }

            @Override
            public boolean hasPrevious() {
                return bIndex > -1;
            }

            @Override
            public Student previous() {
                return get(bIndex--);
            }

            @Override
            public int nextIndex() {
                return 0;
            }

            @Override
            public int previousIndex() {
                return 0;
            }

            @Override
            public void remove() {

            }

            @Override
            public void set(Student student) {

            }

            @Override
            public void add(Student student) {

            }
        };
    }

    @Override
    public ListIterator<Student> listIterator(int i) {
        return new ListIterator<Student>() {
            private int fIndex = i;
            private int bIndex = size - 1;

            @Override
            public boolean hasNext() {
                return fIndex < size;
            }

            @Override
            public Student next() {
                return get(fIndex++);
            }

            @Override
            public boolean hasPrevious() {
                return bIndex > -1;
            }

            @Override
            public Student previous() {
                return get(bIndex--);
            }

            @Override
            public int nextIndex() {
                return 0;
            }

            @Override
            public int previousIndex() {
                return 0;
            }

            @Override
            public void remove() {

            }

            @Override
            public void set(Student student) {

            }

            @Override
            public void add(Student student) {
            }
        };
    }

    @Override
    public List<Student> subList(int i, int i1) {
        checkIndex(i); checkIndex(i1 - 1);
        List<Student> subList = new ArrayList<>();
        Student el = get(i);
        while (i++ < i1){
            subList.add(el);
            el = el.getNext();
        }
        return subList;
    }

    @Override
    public boolean addAll(Collection<? extends Student> collection) {
        collection.forEach(this::add);
        return true;
    }

    private void checkIndex(int i) {
        if(i < 0 || i >= size)
            throw new IndexOutOfBoundsException("Please check the index.");
    }

    private void deleteData(Student s) {
        s.setName(null);
        s.setDateOfBirth(null);
        s.setDetails(null);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int i, Collection<? extends Student> collection) {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }
}
