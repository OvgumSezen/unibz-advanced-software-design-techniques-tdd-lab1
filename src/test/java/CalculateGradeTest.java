import org.asdt.Grade;
import org.junit.jupiter.api.Test;

import static org.asdt.CalculateGrade.calculateGrade;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CalculateGradeTest {
    @Test
    void examGradeValidTest() throws Exception {
        int labPoints = 27;
        float examGrade = 3.2F;

        float weightedExamGrade = (examGrade * 15) / 10;
        float weightedLabPoints = (float) (labPoints * 15) / 50;

        Grade grade = calculateGrade(labPoints, examGrade);
        assertNotNull(grade);

        assertEquals(Math.round(grade.getGrade() - weightedLabPoints), Math.round(weightedExamGrade));
    }

    @Test
    void examGradeLargerThan10Test() {
        int labPoints = 15;
        float examGrade = 11.5F;
        String expectedExceptionMessage = "Exam grade should be between 0 and 10.";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> calculateGrade(labPoints, examGrade));
        String actualExceptedMessage = exception.getMessage();

        assertTrue(actualExceptedMessage.contains(expectedExceptionMessage));
    }

    @Test
    void examGradeLessThan0Test() {
        int labPoints = 15;
        float examGrade = -1.5F;
        String expectedExceptionMessage = "Exam grade should be between 0 and 10.";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> calculateGrade(labPoints, examGrade));
        String actualExceptionMessage = exception.getMessage();

        assertTrue(actualExceptionMessage.contains(expectedExceptionMessage));
    }

    @Test
    void labPointsHalfOfGradeTest() throws Exception {
        int labPoints = 27;
        float examGrade = 3.2F;

        float weightedExamGrade = (examGrade * 15) / 10;
        float weightedLabPoints = (float) (labPoints * 15) / 50;

        Grade grade = calculateGrade(labPoints, examGrade);
        assertNotNull(grade);

        assertEquals( Math.round(grade.getGrade() - weightedExamGrade), Math.round(weightedLabPoints));
    }

    @Test
    void labPointsNonNegativeTest() {
        int labPoints = -1;
        float examGrade = 8F;
        String expectedExceptionMessage = "Lab Points should be non-negative.";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> calculateGrade(labPoints, examGrade));
        String actualExceptionMessage = exception.getMessage();

        assertTrue(actualExceptionMessage.contains(expectedExceptionMessage));
    }

    @Test
    void unapprovedIfGradeLessThan18Test() throws Exception {
        int labPoints = 1;
        float examGrade = 1F;

        Grade grade = calculateGrade(labPoints, examGrade);
        assertNotNull(grade);

        assertFalse(grade.isApproved());
    }

    @Test
    void approvedIfGradeGreaterThan18Test() throws Exception {
        int labPoints = 50;
        float examGrade = 10F;

        Grade grade = calculateGrade(labPoints, examGrade);
        assertNotNull(grade);

        assertTrue(grade.isApproved());
    }

    @Test
    void cumLaudeIfGradeGreaterThan30Test() throws Exception {
        int labPoints = 55;
        float examGrade = 10F;
        int expectedGrade = getExpectedFinalGrade(labPoints, examGrade);

        Grade grade = calculateGrade(labPoints, examGrade);
        assertNotNull(grade);

        assertTrue(grade.getGrade() >= expectedGrade);
        assertTrue(grade.isCumLaude());
    }

    @Test
    void notCumLaudeIfGradeLessThan30Test() throws Exception {
        int labPoints = 5;
        float examGrade = 1F;
        int expectedGrade = getExpectedFinalGrade(labPoints, examGrade);

        Grade grade = calculateGrade(labPoints, examGrade);
        assertNotNull(grade);

        assertEquals(grade.getGrade(), expectedGrade);
        assertFalse(grade.isCumLaude());
    }

    @Test
    void labPointBonusEffectiveTest() throws Exception {
        int labPoints = 57;
        float examGrade = 5.3F;
        float expectedGrade = getExpectedFinalGrade(labPoints, examGrade);

        Grade grade = calculateGrade(labPoints, examGrade);
        assertNotNull(grade);

        assertEquals(expectedGrade, grade.getGrade());
    }

    @Test
    void labPointBonusEffective_whenBonusIsGreaterThan3() throws Exception {
        int labPoints = 66;
        float examGrade = 5.3F;
        float expectedGrade = getExpectedFinalGrade(labPoints, examGrade);

        Grade grade = calculateGrade(labPoints, examGrade);
        assertNotNull(grade);

        assertEquals(expectedGrade, grade.getGrade());
    }

    @Test
    void gradeRoundedDown_whenLabPointsLessThan25Test() throws Exception {
        int labPoints = 10;
        float examGrade = 5.7F;
        int expectedGrade = getExpectedFinalGrade(labPoints, examGrade);

        Grade grade = calculateGrade(labPoints, examGrade);
        assertNotNull(grade);

        assertEquals(expectedGrade, grade.getGrade());
    }

    @Test
    void gradeRoundedDown_whenLabPointsBetween25And40Test() throws Exception {
        int labPoints = 30;
        float examGrade = 5.3F;
        int expectedGrade = getExpectedFinalGrade(labPoints, examGrade);

        Grade grade = calculateGrade(labPoints, examGrade);
        assertNotNull(grade);

        assertEquals(expectedGrade, grade.getGrade());
    }

    @Test
    void gradeRoundedUp_whenLabPointsLessThan25Test() throws Exception {
        int labPoints = 30;
        float examGrade = 5.7F;
        int expectedGrade = getExpectedFinalGrade(labPoints, examGrade);

        Grade grade = calculateGrade(labPoints, examGrade);
        assertNotNull(grade);

        assertEquals(expectedGrade, grade.getGrade());
    }

    @Test
    void gradeRoundedUp_whenLabPointsGreaterThan40Test() throws Exception {
        int labPoints = 45;
        float examGrade = 5.1F;
        int expectedGrade = getExpectedFinalGrade(labPoints, examGrade);

        Grade grade = calculateGrade(labPoints, examGrade);
        assertNotNull(grade);

        assertEquals(expectedGrade, grade.getGrade());
    }

    private int getExpectedFinalGrade(int labPoints, float examGrade) {
        if(labPoints > 50 && labPoints <= 65) {
            examGrade += (float) Math.floor( (float) (labPoints - 50) / 5);
        } else if(labPoints > 65) {
            examGrade += 3;
        }

        float weightedExamGrade = (examGrade * 15) / 10;
        float weightedLabPoints = (float) (labPoints * 15) / 50;

        float decimalExpectedGrade = weightedExamGrade + weightedLabPoints;

        if(labPoints < 25) {
            return (int) Math.floor(decimalExpectedGrade);
        } else if (labPoints > 40) {
            return (int) Math.ceil(decimalExpectedGrade);
        } else {
            return Math.round(decimalExpectedGrade);
        }
    }
}
