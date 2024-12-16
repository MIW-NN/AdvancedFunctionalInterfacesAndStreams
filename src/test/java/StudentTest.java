import model.Course;
import model.Exam;
import model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    private Course course0, course1, course2;
    private Student student0, student1, student2;
    private Exam exam1a, exam1b, exam2;

    @BeforeEach
    public void setup() {
        this.student0 = new Student(500999003, "L. Jussen");
        this.student1 = new Student(500999004, "A. Jussen");
        this.student2 = new Student(500999001, "W. Soerjadi");
        this.course0 = new Course("C00", "Piano voor beginners", 3);
        this.course1 = new Course("C01", "Piano voor gevorderden", 4);
        this.course2 = new Course("C02", "Piano Concert", 6);

        student0.getRequirements().add(this.course1);
        student0.getRequirements().add(this.course2);
        student1.getRequirements().add(this.course1);
        student1.getRequirements().add(this.course2);
        student2.getRequirements().add(this.course1);

        this.exam1a = new Exam("2019-08-25", this.course1);
        this.exam1b = new Exam("2019-09-02", this.course1);
        this.exam2 = new Exam("2019-09-02", this.course2);

        this.exam1a.addResult(this.student0, 4.0);
        this.exam1a.addResult(this.student1, 5.5);
        this.exam1b.addResult(this.student0, 7.0);
        this.exam1b.addResult(this.student1, 9.0);
        this.exam1b.addResult(this.student2, 4.0);
        this.exam2.addResult(this.student0, 8.0);
        this.exam2.addResult(this.student1, 4.0);
    }

    @Test
    void T03_checkStudentgetRequiredECTS() {
        assertEquals(10, this.student0.getRequiredECTS(), "requiredECTS student0");
        assertEquals(10, this.student1.getRequiredECTS(), "requiredECTS student1");
        assertEquals(4, this.student2.getRequiredECTS(), "requiredECTS student2");
    }

    @Test
    void T06_checkStudentgetEarnedECTS() {
        assertEquals(10, this.student0.getEarnedECTS(), "earnedECTS student0");
        assertEquals(4, this.student1.getEarnedECTS(), "earnedECTS student1");
        assertEquals(0, this.student2.getEarnedECTS(), "earnedECTS student2");
    }

    @Test
    void T04_checkBestExamResult() {
        assertEquals(0.0, this.student0.getBestResult(this.course0));
        assertEquals(7.0, this.student0.getBestResult(this.course1));
        assertEquals(8.0, this.student0.getBestResult(this.course2));
        assertEquals(9.0, this.student1.getBestResult(this.course1));
        assertEquals(4.0, this.student1.getBestResult(this.course2));
        assertEquals(4.0, this.student2.getBestResult(this.course1));
        assertEquals(0.0, this.student2.getBestResult(this.course2));
    }

    @Test
    void T05_checkHasPassed() {
        assertFalse(this.student0.hasPassed(this.course0));
        assertTrue(this.student0.hasPassed(this.course1));
        assertTrue(this.student0.hasPassed(this.course2));
        assertTrue(this.student1.hasPassed(this.course1));
        assertFalse(this.student1.hasPassed(this.course2));
        assertFalse(this.student2.hasPassed(this.course1));
        assertFalse(this.student2.hasPassed(this.course2));
    }
}