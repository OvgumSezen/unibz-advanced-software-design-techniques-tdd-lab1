package org.asdt.exception;

public class ExamGradeValidationException extends Exception {
    private String message;

    public ExamGradeValidationException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
