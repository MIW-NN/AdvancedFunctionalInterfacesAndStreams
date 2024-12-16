package model;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

public class SIS {
    // TODO: Choose the correct solution variant
    // solutionVariant == 0: execute no solution
    // solutionVariant == 1: execute for-loop solution
    // solutionVariant > 1: execute .forEach() solution
    // solutionVariant < 0: execute .stream() solution
    public static SolutionVariant solutionVariant = SolutionVariant.FOR_LOOP;

    private static Random randomizer = new Random();

    private Set<Course> courses;
    private Set<Student> students;

    @Override
    public String toString() {
        return String.format("SIS_c%d_s%d", this.courses.size(), this.students.size());
    }

    private SIS() {
        this.courses = new HashSet<>();
        this.students = new HashSet<>();
    }

    /**
     * counts the number of students that have this course as part of their requirements
     *
     * @param course
     * @return
     */
    public int getNStudentsRequired(Course course) {
        int nStudents = 0;

        // TODO-1a count the students using a for-loop
        if (SIS.solutionVariant == SolutionVariant.FOR_LOOP) {

        }

        // TODO-1b count the students using .forEach
        if (SIS.solutionVariant == SolutionVariant.FOREACH) {
            var ref = new Object() {
                int nStudents = 0;
            };
            return ref.nStudents;
        }

        // TODO-2 count the students using .stream()
        if (SIS.solutionVariant == SolutionVariant.STREAM) {

        }

        return nStudents;
    }

    /**
     * counts the number of students that have passed an exam of this course
     *
     * @param course
     * @return
     */
    public int getNStudentsPassed(Course course) {
        int nStudents = 0;

        // TODO-1a count the students using a for-loop
        if (SIS.solutionVariant == SolutionVariant.FOR_LOOP) {

        }

        // TODO-1b count the students using .forEach
        if (SIS.solutionVariant == SolutionVariant.FOREACH) {
            var ref = new Object() {
                int nStudents = 0;
            };

            return ref.nStudents;
        }

        // TODO-2 count the students using .stream()
        if (SIS.solutionVariant == SolutionVariant.STREAM) {

        }

        return nStudents;
    }


    /**
     * model.SIS constructor that generates a model.SIS with random data
     *
     * @param nCourses                 number of courses in the model.SIS
     * @param nStudents                number of students in the model.SIS
     * @param nExams                   number of previous exams; these will be distributed randomly across courses and dates
     * @param averageRequiredECTS      Students will be given random model.Course requirements which collectively adhere to
     *                                 the specified average number of ECTS
     * @param averageExamParticipation Students will be given random model.Exam results
     *                                 The examParticipationFactor of students will adhere to the specified average
     */
    public SIS(int nCourses, int nStudents, int nExams,
               int averageRequiredECTS, double averageExamParticipation) {
        this();
        for (int i = 0; i < nCourses; i++) {
            this.courses.add(Course.getARandomCourse());
        }

        List<Course> courses = new ArrayList<>(this.courses);
        for (int i = 0; i < nStudents; i++) {
            Student student = new Student();
            int targetECTS = randomizer.nextInt(2 * averageRequiredECTS);
            int nRemainingCourses = courses.size();
            while (nRemainingCourses > 0 &&
                    student.getRequiredECTS() < targetECTS) {
                int targetIdx = randomizer.nextInt(nRemainingCourses);
                nRemainingCourses--;
                Course course = courses.get(targetIdx);
                student.getRequirements().add(course);
                courses.set(targetIdx, courses.get(nRemainingCourses));
                courses.set(nRemainingCourses, course);
            }
            this.students.add(student);
        }
        for (int e = 0; e < nExams; e++) {
            Course course = courses.get(randomizer.nextInt(this.courses.size()));
            Exam exam = new Exam(course);
            for (Student s : this.students) {
                if (s.requires(course) &&
                        randomizer.nextDouble() < averageExamParticipation) {
                    exam.addResult(s, 1.0 + 0.1 * randomizer.nextInt(91));
                }
            }
        }
    }

    /**
     * Schedules additional exams for courses in the future.
     * No participation of students will be booked yet and no results are registered
     *
     * @param nExams
     */
    public void scheduleAdditionalExams(int nExams) {
        List<Course> courses = new ArrayList<>(this.courses);
        int nRemainingCourses = Math.min(nExams, courses.size());
        while (nRemainingCourses > 0) {
            int targetIdx = randomizer.nextInt(nRemainingCourses);
            nRemainingCourses--;
            Course course = courses.get(targetIdx);
            courses.set(targetIdx, courses.get(nRemainingCourses));
            courses.set(nRemainingCourses, course);

            Exam exam = new Exam(LocalDate.now().plusDays(50 + randomizer.nextInt(15)), course);
        }
    }

    /**
     * finds all Exams in the model.SIS that match a given filter criterium
     *
     * @param filter
     * @return
     */
    public Set<Exam> findAllExams(Predicate<Exam> filter) {
        Set<Exam> foundExams = new TreeSet<Exam>();

        // TODO-1: find all exams using .forEach()
        if (SIS.solutionVariant == SolutionVariant.FOR_LOOP || SIS.solutionVariant == SolutionVariant.FOREACH) {

        }

        // TODO-2: find all exams using .stream()
        if (SIS.solutionVariant == SolutionVariant.STREAM) {

        }
        return foundExams;
    }

    /**
     * Calculates the total study requirements per model.Student based on their model.Course requirements
     *
     * @return
     */
    public Map<Student, Integer> calculateRequiredEctsPerStudent() {
        Map<Student, Integer> map = new HashMap<>();

        // TODO-1 build the map of required ects using .forEach()
        if (SIS.solutionVariant == SolutionVariant.FOR_LOOP || SIS.solutionVariant == SolutionVariant.FOREACH) {

        }

        // TODO-2 build the map of required ects using .stream()
        if (SIS.solutionVariant == SolutionVariant.STREAM) {

        }

        return map;
    }

    /**
     * Calculates the total number of ects earned per model.Student, based on their model.Exam results
     *
     * @return
     */
    public Map<Student, Integer> calculateTotalEarnedEctsPerStudent() {
        Map<Student, Integer> map = new HashMap<>();

        // TODO-1 build the map of earned ects using .forEach()
        if (SIS.solutionVariant == SolutionVariant.FOR_LOOP || SIS.solutionVariant == SolutionVariant.FOREACH) {

        }

        // TODO-2 build the map of required ects using .stream()
        if (SIS.solutionVariant == SolutionVariant.STREAM) {

        }
        return map;
    }

    /**
     * Calculates remaining ECTS to be earned per student based on their study requirements and exam results
     *
     * @return
     */
    public Map<Student, Integer> calculateRemainingEctsPerStudent() {
        Map<Student, Integer> map = this.calculateRequiredEctsPerStudent();
        // TODO-1 use map.replaceAll to calculate the remaining ects for each student

        return map;
    }

    /**
     * Calculates the expected maximum participation of a future model.Exam based on current study results.
     * It counts per model.Exam all students that have the model.Course on their requirements,
     * but not yet passed an model.Exam of the model.Course
     *
     * @param filter
     * @return
     */
    public Map<Exam, Integer> calculateMaxExamParticipation(Predicate<Exam> filter) {
        Map<Exam, Integer> maxParticipation = new TreeMap<>();

        // TODO-1 calculate maximum expected participation per exam using .forEach()
        if (SIS.solutionVariant == SolutionVariant.FOR_LOOP || SIS.solutionVariant == SolutionVariant.FOREACH) {

        }

        // TODO-2 calculate maximum expected participation per exam using .stream() and .collect()
        if (SIS.solutionVariant == SolutionVariant.STREAM) {

        }

        return maxParticipation;
    }

    public Set<Course> getCourses() {
        return this.courses;
    }

    public Set<Student> getStudents() {
        return this.students;
    }

    /**
     * A builder helper class to compose a small model.SIS using method-chaining of builder methods
     */
    public static class Builder {
        SIS sis = new SIS();

        private Course getOrAdd(Course course) {
            for (Course c : this.sis.courses) {
                if (c.equals(course)) {
                    return c;
                }
            }
            this.sis.courses.add(course);
            return course;
        }

        private Student getOrAdd(Student student) {
            for (Student s : this.sis.students) {
                if (s.equals(student)) {
                    return s;
                }
            }
            this.sis.students.add(student);
            return student;
        }

        private Exam getOrAdd(String dateString, String courseCode) {
            Course course = this.getOrAdd(new Course(courseCode));
            Exam exam = course.getExamAt(dateString);
            if (exam == null) exam = new Exam(dateString, course);
            return exam;
        }

        /**
         * Builder method to add a course to the model.SIS being build
         *
         * @param course
         * @return
         */
        public Builder addCourse(Course course) {
            this.getOrAdd(course);
            return this;
        }

        /**
         * Builder method to add a student to the model.SIS being build
         * Also adds the specified courses to the study requirements of the student
         * Non-existing course codes will also be added to the model.SIS, if needed
         *
         * @param student
         * @param courseCodes
         * @return
         */
        public Builder addStudent(Student student, String... courseCodes) {
            Student student0 = this.getOrAdd(student);
            for (String code : courseCodes) {
                student0.getRequirements()
                        .add(this.getOrAdd(new Course(code)));
            }
            return this;
        }

        /**
         * Builder method to add an model.Exam to the specified course in the model.SIS
         * if the course does not yet exist it will be added first
         * if the course already has an exam on the same day, no additional exam should be added
         *
         * @param dateString
         * @param courseCode
         * @return
         */
        public Builder addExam(String dateString, String courseCode) {
            this.getOrAdd(dateString, courseCode);
            return this;
        }

        /**
         * Builder method to add an exam result to the model.SIS
         * If a specified model.Exam, model.Course or model.Student is missing, these will first be added
         *
         * @param dateString
         * @param courseCode
         * @param studentNr
         * @param grade
         * @return
         */
        public Builder addResult(String dateString, String courseCode, int studentNr, double grade) {
            Exam exam = this.getOrAdd(dateString, courseCode);
            Student student = this.getOrAdd(new Student(studentNr));
            exam.addResult(student, grade);
            return this;
        }

        /**
         * Complete the model.SIS being build
         *
         * @return
         */
        public SIS build() {
            return this.sis;
        }
    }
}