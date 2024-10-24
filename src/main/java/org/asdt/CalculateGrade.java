package org.asdt;

import org.asdt.exception.ExamGradeValidationException;
import org.asdt.exception.LabPointsValidationException;

//TEMP COMMENT

public class CalculateGrade {
    public static Grade calculateGrade(int labPoints, float examGrade) throws Exception {
        checkInputValidity(labPoints, examGrade);

        examGrade = addLabPointBonus(labPoints, examGrade);

        float decimalGrade = calculateDecimalGrade(labPoints, examGrade);
        int roundedGrade = roundDecimalExamGrade(labPoints, decimalGrade);

        return new Grade((roundedGrade > 18), roundedGrade, (roundedGrade > 30));
    }

    private static void checkInputValidity(int labPoints, float examGrade) throws Exception {
        if(labPoints < 0) {
            throw new LabPointsValidationException("Lab Points should be non-negative.");
        }

        if(examGrade < 0 || examGrade > 10) {
            throw new ExamGradeValidationException("Exam grade should be between 0 and 10.");
        }
    }

    private static float addLabPointBonus(int labPoints, float examGrade) {
        if(labPoints > 65) {
            return examGrade + 3;
        }

        if(labPoints > 50) {
            return (float) (examGrade + Math.floor((float) (labPoints - 50) / 5));
        }

        return examGrade;
    }

    private static float calculateDecimalGrade(int labPoints, float examGrade) {
        float weightedExamGrade = (examGrade * 15) / 10;
        float weightedLabPoints = (float) (labPoints * 15) / 50;

        return weightedExamGrade + weightedLabPoints;
    }

    private static int roundDecimalExamGrade(int labPoints, float decimalGrade) {
        if(labPoints < 25) {
            return (int) Math.floor(decimalGrade);
        }

        if (labPoints > 40) {
            return (int) Math.ceil(decimalGrade);
        }

        return Math.round(decimalGrade);
    }
}
