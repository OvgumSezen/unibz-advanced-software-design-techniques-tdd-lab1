package org.asdt;

public class Grade {
    private boolean approved;
    private int grade;
    private boolean cumLaude;

    public Grade(boolean approved, int grade, boolean cumLaude) {
        this.approved = approved;
        this.grade = grade;
        this.cumLaude = cumLaude;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public boolean isCumLaude() {
        return cumLaude;
    }

    public void setCumLaude(boolean cumLaude) {
        this.cumLaude = cumLaude;
    }
}
