package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Exam implements Comparable<Exam> {
    private static Random randomizer = new Random();
    private LocalDate date;
    private Course course;
    private LocalTime time;
    private Map<Student, Double> results;

    public Exam(LocalDate date, Course course) {
        this.date = date;
        this.course = course;
        this.time = getARandomTime();
        this.results = new HashMap<>();
        this.course.getExams().add(this);
    }
    public Exam(String dateString, Course course) {
        this(LocalDate.parse(dateString), course);
    }

    public Exam(Course course) {
        this(Exam.getARandomDate(course), course);
    }

    @Override
    public String toString() {
        return String.format("E_%s(%s)", this.date.format(DateTimeFormatter.ISO_DATE), this.course.getCode());
    }

    @Override
    public boolean equals(Object o) {
        return this.date.equals(((Exam)o).date) && this.course.equals(((Exam)o).course);
    }

    @Override
    public int hashCode() {
        return this.date.hashCode() + 701 * this.course.hashCode();
    }

    @Override
    public int compareTo(Exam o) {
        int result = this.date.compareTo(o.date);;
        if (result == 0) {
            result = this.course.compareTo(o.course);
        }
        return result;
    }

    /**
     * Adds a result by a model.Student or overwrites a previous registration of a result by the same student.
     * Also updates the model.Student's requirements and exam participation if required
     * @param student
     * @param grade
     * @return
     */
    public Exam addResult(Student student, double grade) {
        student.getRequirements().add(this.getCourse());
        student.getExams().add(this);
        this.results.put(student, grade);

        return this;
    }

    /**
     * finds a suitable random date for a new model.Exam of a model.Course
     * (on a day where no model.Exam has been scheduled already)
     * @param course
     * @return
     */
    public static LocalDate getARandomDate(Course course) {
        LocalDate examDate;
        do {
            examDate = LocalDate.now().minusDays(randomizer.nextInt(1000));
        } while (course.getExamAt(examDate) != null);
        return examDate;
    }

    public static LocalTime getARandomTime() {
        return LocalTime.NOON.plusHours(randomizer.nextInt(10)-3);
    }

    public Course getCourse() {
        return course;
    }

    public Map<Student, Double> getResults() {
        return results;
    }

    public LocalDate getDate() {
        return date;
    }
}
