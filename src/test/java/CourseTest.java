import model.Course;
import model.Exam;
import model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
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
    void T01_checkGetExamAt() {
        assertEquals(this.exam1a, this.course1.getExamAt("2019-08-25"),"exam at date");
        assertNull(this.course1.getExamAt("2019-08-26"),"no exam at date");
    }
    @Test
    void T11_checkFindExams() {
        assertEquals(Set.of(this.exam1a,this.exam1b),
                this.course1.findExams(e -> e.getDate().isAfter(LocalDate.parse("2019-07-01"))),
                "course1 exams after 2019-07-01");
        assertEquals(Set.of(this.exam1a),
                this.course1.findExams(e -> e.getDate().isBefore(LocalDate.parse("2019-09-01"))),
                "course1 exams before 2019-09-01");
        assertEquals(Set.of(),
                this.course1.findExams(e -> e.getDate().isBefore(LocalDate.parse("2019-07-01"))),
                "course1 exams before 2019-07-01");
    }

    @Test
    void T12_checkGetAverageGrade() {
        assertEquals(0.0, this.course0.getAverageGrade(),"average grade course0");
        assertEquals(5.9, this.course1.getAverageGrade(),"average grade course1");
        assertEquals(6.0, this.course2.getAverageGrade(),"average grade course2");
    }
}