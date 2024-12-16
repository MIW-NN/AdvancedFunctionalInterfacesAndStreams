package model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class Student implements Comparable<Student> {
    private static Random randomizer = new Random();
    private static int lastNumber = 500900000;

    private int number;
    private String name;
    private Set<Course> requirements;
    private Set<Exam> exams;

    public Student(int number) {
        this.number = number;
        lastNumber = Math.max(number, lastNumber);
        this.requirements = new HashSet<>();
        this.exams = new TreeSet<>();
    }

    public Student(int number, String name) {
        this(number);
        this.name = name;
    }

    public Student() {
        this(lastNumber + 1 + randomizer.nextInt(12),
                Names.nextFirstName() + " " + Names.nextSurname());
    }

    @Override
    public String toString() {
        return String.format("%s(%d)", this.name, this.number);
    }

    @Override
    public boolean equals(Object o) {
        return this.number == ((Student)o).number;
    }

    @Override
    public int hashCode() {
        return this.number;
    }

    @Override
    public int compareTo(Student o) {
        return this.number - o.number;
    }

    /**
     * indicates whether a course is part of the requirements of a model.Student
     * @param course
     * @return
     */
    public boolean requires(Course course) {
        return this.requirements.contains(course);
    }

    /**
     * indicates whether a model.Student has passed a model.Course
     * @param course
     * @return
     */
    public boolean hasPassed(Course course) {
        return this.getBestResult(course) >= 5.5;
    }

    /**
     * retrieves the best model.Exam result of the model.Student for the given model.Course
     * @param course
     * @return      returns 0.0 if no model.Exam result was registered for the model.Student yet
     */
    public double getBestResult(Course course) {
        double bestResult = 0;

        // TODO-1 calculate best result using a for-loop
        if (SIS.solutionVariant == SolutionVariant.FOR_LOOP || SIS.solutionVariant == SolutionVariant.FOREACH) {
            for (Exam exam : exams) {
                if (exam.getCourse().equals(course)) {
                    double result = exam.getResults().get(this);
                    if (result > bestResult) {
                        bestResult = result;
                    }
                }
            }
        }

        // TODO-2 calculate best result using .stream()
        if (SIS.solutionVariant == SolutionVariant.STREAM) {

        }

        return bestResult;
    }

    /**
     * Calculates the total number of ECTS of all model.Course requirements of a model.Student
     * @return
     */
    public int getRequiredECTS() {
        int ects = 0;

        // TODO-1a calculate total of required ECTS using a for-loop
        if (SIS.solutionVariant == SolutionVariant.FOR_LOOP) {
            for (Course requirement : requirements) {
                ects += requirement.getEcts();
            }
        }

        // TODO-1b calculate total of required ECTS using a .forEach()
        if (SIS.solutionVariant == SolutionVariant.FOREACH) {
            var ectsObj = new Object() {
                int value = 0;
            };

            requirements.forEach(course -> ectsObj.value += course.getEcts());

            return ectsObj.value;
        }

        // TODO-2 calculate total of required ECTS using .stream()
        if (SIS.solutionVariant == SolutionVariant.STREAM) {

        }

        return ects;
    }

    /**
     * Calculates the total number of earned ECTS of the student from Courses with a passed model.Exam
     * @return
     */
    public int getEarnedECTS() {
        int ects = 0;

        // TODO-1a calculate total of earned ECTS using a for-loop
        if (SIS.solutionVariant == SolutionVariant.FOR_LOOP) {
            for (Course course : requirements) {
                if (hasPassed(course)) {
                    ects += course.getEcts();
                }
            }
        }

        // TODO-1b calculate total of earned ECTS using .forEach()
        if (SIS.solutionVariant == SolutionVariant.FOREACH) {
            var ectsObj = new Object() {
                int value = 0;
            };

            requirements.forEach(course -> {if (hasPassed(course)) {ectsObj.value += course.getEcts();}});

            return ectsObj.value;
        }

        // TODO-2 calculate total of earned ECTS using .stream()
        if (SIS.solutionVariant == SolutionVariant.STREAM) {

        }

        return ects;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Set<Course> getRequirements() {
        return requirements;
    }

    public Set<Exam> getExams() {
        return exams;
    }
}
