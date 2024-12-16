import model.Course;
import model.Exam;
import model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExamTest {
    private Exam exam1a;
    private Exam exam1b;
    private Exam exam2;

    @BeforeEach
    public void setup() {
        Student student0 = new Student(500999003, "L. Jussen");
        Student student1 = new Student(500999004, "A. Jussen");
        Student student2 = new Student(500999001, "W. Soerjadi");
        Course course0 = new Course("C00", "Piano voor beginners", 3);
        Course course1 = new Course("C01", "Piano voor gevorderden", 4);
        Course course2 = new Course("C02", "Piano Concert", 6);
        this.exam1a = new Exam("2019-08-25", course1);
        this.exam1b = new Exam("2019-09-02", course1);
        this.exam2 = new Exam("2019-09-02", course2);
        this.exam1a.addResult(student0, 4.0);
        this.exam1a.addResult(student1, 5.5);
        this.exam1b.addResult(student0, 7.0);
        this.exam1b.addResult(student1, 9.0);
        this.exam1b.addResult(student2, 4.0);
        this.exam2.addResult(student0, 8.0);
        this.exam2.addResult(student1, 4.0);
    }

    @Test
    void T02_checkAddResult() {
        assertEquals("{A. Jussen(500999004)=5.5, L. Jussen(500999003)=4.0}",
                this.exam1a.getResults().toString(), "results exam1a");
        assertEquals("{A. Jussen(500999004)=9.0, W. Soerjadi(500999001)=4.0, L. Jussen(500999003)=7.0}",
                this.exam1b.getResults().toString(), "results exam1b");
        assertEquals("{A. Jussen(500999004)=4.0, L. Jussen(500999003)=8.0}",
                this.exam2.getResults().toString(), "results exam2");
    }
}