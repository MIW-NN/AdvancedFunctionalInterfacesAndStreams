import model.Course;
import model.Exam;
import model.SIS;
import model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SISTest {
    Course course1, course2;
    private SIS sis;

    @BeforeEach
    void setup() {
        course1 = new Course("C01", "Classic piano beginners", 3);
        course2 = new Course("C02", "Classic piano advanced", 4);
        this.sis =
                new SIS.Builder()
                    .addCourse(this.course1)
                    .addCourse(this.course2)
                    .addCourse(new Course("C03", "Piano concert", 6))
                    .addStudent(new Student(500999001, "P. van Vollenhoven"))
                    .addStudent(new Student(500999002, "W. Soerjadi"), "C01")
                    .addStudent(new Student(500999003, "L. Jussen"), "C01", "C02", "C03")
                    .addStudent(new Student(500999004, "A. Jussen"), "C01", "C02", "C03")
                    .addResult("2019-09-02", "C01", 500999001, 4.0)
                    .addResult("2019-09-02", "C01", 500999002, 9.0)
                    .addResult("2019-09-02", "C01", 500999003, 10.0)
                    .addResult("2019-11-01", "C01", 500999001, 5.6)
                    .addResult("2019-11-01", "C01", 500999004, 5.6)
                    .addExam("2019-11-01", "C01")
                    .addExam("2019-11-05", "C03")
                    .addExam("2019-11-05", "C04")
                    .addResult("2019-11-05", "C03", 500999003, 8.5)
                    .addResult("2019-11-05", "C03", 500999004, 8.4)
                    .addExam("2019-11-11", "C02")
                    .build();
    }

    @Test
    void T21_checkSISBuilder() {
        assertEquals(4, this.sis.getCourses().size(),"Courses should be unique by code");
        assertEquals(4, this.sis.getStudents().size(),"Students should be unique by number");
        assertEquals(5, this.sis.findAllExams(x -> true).size(),"Exams should be unique by course and date");

        assertEquals(Set.of("Classic piano beginners", "Classic piano advanced", "Piano concert", ""),
                this.sis.getCourses().stream().map(Course::getTitle)
                        .collect(Collectors.toSet()));

        assertEquals(Set.of("A. Jussen", "P. van Vollenhoven", "W. Soerjadi", "L. Jussen"),
                this.sis.getStudents().stream().map(Student::getName)
                        .collect(Collectors.toSet()));
    }

    @Test
    void T22_findAllExams() {
        assertEquals(Set.of(new Exam("2019-09-02", new Course("C01")),
                new Exam("2019-11-01", new Course("C01")),
                new Exam("2019-11-05", new Course("C03")),
                new Exam("2019-11-05", new Course("C04")),
                new Exam("2019-11-11", new Course("C02"))),
                this.sis.findAllExams(x->true),"finding all exams");
    }

    @Test
    void T23_getNStudentsRequired() {
        assertEquals(4, this.sis.getNStudentsRequired(this.course1));
        assertEquals(2, this.sis.getNStudentsRequired(this.course2));
        assertEquals(2, this.sis.getNStudentsRequired(new Course("C03")));
        assertEquals(0, this.sis.getNStudentsRequired(new Course("C04")));
    }

    @Test
    void T24_getNStudentsPassed() {
        assertEquals(4, this.sis.getNStudentsPassed(this.course1));
        assertEquals(0, this.sis.getNStudentsPassed(this.course2));
        assertEquals(2, this.sis.getNStudentsPassed(new Course("C03")));
        assertEquals(0, this.sis.getNStudentsPassed(new Course("C04")));
    }

    @Test
    void T31_calculateRequiredEctsPerStudent() {
        Map<Student,Integer> map =
                this.sis.calculateRequiredEctsPerStudent();
        assertEquals("{A. Jussen(500999004)=13, P. van Vollenhoven(500999001)=3, W. Soerjadi(500999002)=3, L. Jussen(500999003)=13}",
                map.toString());
    }

    @Test
    void T32_calculateTotalEarnedEctsPerStudent() {
        Map<Student, Integer> map =
                this.sis.calculateTotalEarnedEctsPerStudent();
        assertEquals("{A. Jussen(500999004)=9, P. van Vollenhoven(500999001)=3, W. Soerjadi(500999002)=3, L. Jussen(500999003)=9}",
                map.toString());
    }

    @Test
    void T33_calculateRemainingEctsPerStudent() {
        Map<Student,Integer> map =
                this.sis.calculateRemainingEctsPerStudent();
        assertEquals("{A. Jussen(500999004)=4, P. van Vollenhoven(500999001)=0, W. Soerjadi(500999002)=0, L. Jussen(500999003)=4}",
                map.toString());
    }

    @Test
    void T39_calculateMaxExamParticipation() {
        Map<Exam,Integer> map =
                this.sis.calculateMaxExamParticipation(e -> e.getDate().isAfter(LocalDate.parse("2019-11-01")));
        assertEquals(Map.of(new Exam("2019-11-05",new Course("C03")),0,
                new Exam("2019-11-05",new Course("C04")),0,
                new Exam("2019-11-11",new Course("C02")),2),
                map);
    }

    @Test
    void T41_checkRandomSIS() {
        sis = new SIS(20, 25, 50, 20, 0.3);
        assertEquals(50, sis.findAllExams(e->true).size(), "number of exams does not match");

        int totalRequiredCourses = 0;
        int totalPassedCourses = 0;
        for (Student s: sis.getStudents()) {
            totalRequiredCourses += s.getRequirements().size();
            for (Course c: s.getRequirements()) {
                totalPassedCourses += (s.hasPassed(c) ? 1 : 0);
            }
        }
        int totalRequiredStudents = 0;
        int totalPassedStudents = 0;
        for (Course c: sis.getCourses()) {
            totalRequiredStudents += sis.getNStudentsRequired(c);
            totalPassedStudents += sis.getNStudentsPassed(c);
        }
        assertEquals(totalRequiredCourses,totalRequiredStudents,"total student requirements do not match sis requirements");
        assertEquals(totalPassedCourses,totalPassedStudents,"total student progress does not match sis progress");

        Map<Student,Integer> required =
                this.sis.calculateRequiredEctsPerStudent();
        Map<Student,Integer> earned =
                this.sis.calculateTotalEarnedEctsPerStudent();
        Map<Student,Integer> remaining =
                this.sis.calculateRemainingEctsPerStudent();
        required.entrySet().forEach(
                e-> {
                    Student student = e.getKey();
                    int requiredECTS = e.getValue();
                    int earnedECTS = earned.getOrDefault(student,0);
                    int remainingECTS = remaining.getOrDefault(student,0);
                    if (requiredECTS != earnedECTS+remainingECTS) {
                        System.out.printf("%s: required=%d earned=%d remaining=%d\n", student, requiredECTS, earnedECTS, remainingECTS);
                        System.out.println(student.getRequirements());
                        System.out.println(student.getRequirements().stream().mapToDouble(student::getBestResult));
                    }
                    assertAll(
                            () -> assertTrue(requiredECTS >= 0,
                                    "requiredECTS must be greater than or equal to 0, but was " + requiredECTS
                                            + " for student " + student.getName() + " (" + student.getNumber() + ")" ),
                            () -> assertTrue(earnedECTS >= 0,
                                    "earnedECTS must be greater than or equal to 0"),
                            () -> assertTrue(remainingECTS >= 0,
                                    "remainingECTS must be greater than or equal to 0"),
                            () -> assertEquals(requiredECTS,earnedECTS + remainingECTS,
                                    "error in balance: required = earned + remaining")
                    );

                }
        );
    }
}