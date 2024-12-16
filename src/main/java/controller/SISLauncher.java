package controller;

import model.SIS;
import model.Student;

import java.time.LocalDate;
import java.util.*;

public class SISLauncher {

    public static void main(String[] args) {
        SIS sis = new SIS(20, 50, 50, 15, 0.3);

        Map<Student,Integer> requiredEctsPerStudent = sis.calculateRequiredEctsPerStudent();
        System.out.println("Required ECTS per student:");
        System.out.println(requiredEctsPerStudent);

        System.out.println("Earned ECTS per student:");
        Map<Student,Integer> ectsPerStudent = sis.calculateTotalEarnedEctsPerStudent();
        System.out.println(ectsPerStudent);

        System.out.println("Remaining ECTS per student:");
        ectsPerStudent.replaceAll((student,ects) -> student.getRequiredECTS() - ects );
        System.out.println(ectsPerStudent);

        sis.scheduleAdditionalExams(8);
        System.out.println("Expected participation for future exams:");
        System.out.println(sis.calculateMaxExamParticipation(e -> e.getDate().isAfter(LocalDate.now())));
    }
}
