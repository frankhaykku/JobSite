package ru.jobsite.model.resume;


import org.hibernate.validator.constraints.NotBlank;
import ru.jobsite.model.resume.ResumeModel.Resume;

import java.text.SimpleDateFormat;

public class ResumeForm {

    private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

    @NotBlank(message = ResumeForm.NOT_BLANK_MESSAGE)
    private String fullName;

    @NotBlank(message = ResumeForm.NOT_BLANK_MESSAGE)
    private String birthDate;

    @NotBlank(message = ResumeForm.NOT_BLANK_MESSAGE)
    private String sex;

    @NotBlank(message = ResumeForm.NOT_BLANK_MESSAGE)
    private String city;

    @NotBlank(message = ResumeForm.NOT_BLANK_MESSAGE)
    private String phoneNumber;

    @NotBlank(message = ResumeForm.NOT_BLANK_MESSAGE)
    private String nationality;

    @NotBlank(message = ResumeForm.NOT_BLANK_MESSAGE)
    private String crossOver;

    @NotBlank(message = ResumeForm.NOT_BLANK_MESSAGE)
    private String grade;

    @NotBlank(message = ResumeForm.NOT_BLANK_MESSAGE)
    private String universityName;

    @NotBlank(message = ResumeForm.NOT_BLANK_MESSAGE)
    private String faculty;

    @NotBlank(message = ResumeForm.NOT_BLANK_MESSAGE)
    private String specialization;

    @NotBlank(message = ResumeForm.NOT_BLANK_MESSAGE)
    private String year;

    @NotBlank(message = ResumeForm.NOT_BLANK_MESSAGE)
    private String nativeLang;

    @NotBlank(message = ResumeForm.NOT_BLANK_MESSAGE)
    private String anotherLang;

    @NotBlank(message = ResumeForm.NOT_BLANK_MESSAGE)
    private String exp;

    @NotBlank(message = ResumeForm.NOT_BLANK_MESSAGE)
    private String aboutDesc;

    public static String getNotBlankMessage() {
        return NOT_BLANK_MESSAGE;
    }

    public Resume createUserResume(String email) {
        return new Resume(email, getFullName(), getBirthDate(), getSex(), getCity(), getPhoneNumber(), getNationality(),
                getCrossOver(), getGrade(), getUniversityName(), getFaculty(), getSpecialization(), Integer.parseInt(getYear()),
                getNativeLang(), getAnotherLang(), getExp(), getAboutDesc());
    }

    public void convertResumeToForm(Resume resume) {
        fullName = resume.getFullName();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        birthDate = simpleDateFormat.format(resume.getBirthDate().getTime());

        sex = resume.isSex() ? "Женский" : "Мужской";
        city = resume.getCity();
        phoneNumber = resume.getPhoneNumber();
        nationality = resume.getNationality();
        crossOver = resume.isCrossOver() ? "Да" : "Нет";
        grade = resume.getGrade().toString();
        universityName = resume.getUniversityName();
        faculty = resume.getFaculty();
        specialization = resume.getSpecialization();
        year = String.valueOf(resume.getYear());
        nativeLang = resume.getNativeLang();
        anotherLang = resume.getAllAnotherLang();
        exp = resume.isExp() ? "Да" : "Нет";
        aboutDesc = resume.getAboutDescription();
    }
    
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCrossOver() {
        return crossOver;
    }

    public void setCrossOver(String crossOver) {
        this.crossOver = crossOver;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getNativeLang() {
        return nativeLang;
    }

    public void setNativeLang(String nativeLang) {
        this.nativeLang = nativeLang;
    }

    public String getAnotherLang() {
        return anotherLang;
    }

    public void setAnotherLang(String anotherLang) {
        this.anotherLang = anotherLang;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getAboutDesc() {
        return aboutDesc;
    }

    public void setAboutDesc(String aboutDesc) {
        this.aboutDesc = aboutDesc;
    }

    @Override
    public String toString() {
        return "ResumeForm{" +
                "fullName='" + fullName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", sex='" + sex + '\'' +
                ", city='" + city + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", nationality='" + nationality + '\'' +
                ", crossOver='" + crossOver + '\'' +
                ", grade='" + grade + '\'' +
                ", universityName='" + universityName + '\'' +
                ", faculty='" + faculty + '\'' +
                ", specialization='" + specialization + '\'' +
                ", year=" + year +
                ", nativeLang='" + nativeLang + '\'' +
                ", anotherLang='" + anotherLang + '\'' +
                ", exp='" + exp + '\'' +
                ", about='" + aboutDesc + '\'' +
                '}';
    }
}
