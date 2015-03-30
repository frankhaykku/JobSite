package ru.jobsite.model.resume.ResumeModel;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = "resume")
public class Resume {

    @Id
    @GeneratedValue
    private long id;

    private String email;

    private String fullName;            // Полное имя
    private Calendar birthDate;         // Дата рождения
    private boolean sex;                // Пол (false - м, true - ж)
    private String city;                // Город проживания
    private String phoneNumber;         // Номер телефона
    private String nationality;         // Гражданство    
    private boolean crossOver;          // Возможен ли переезд

    private educationGrade grade;       // Уровень обраования
    private String universityName;      // Имя университета
    private String faculty;             // Факультет
    private String specialization;      // Специализация
    private int year;                   // Год окончания

    private String nativeLang;            // Родной язык

    @Transient
    private List<String> anotherLang = new ArrayList<String>();           // Другой язык (владение)

    private boolean exp;                // Опыт работы
    private String aboutDescription;

    public Resume() {
    }

    public Resume(String fullName, String birthDate, String sex, String city, String phoneNumber, String nationality,
                  String crossOver, String grade, String universityName, String faculty, String specialization, int year,
                  String nativeLang, String anotherLang, String exp, String aboutDescription) {
        this.fullName = fullName;
        this.birthDate = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        try {
            this.birthDate.setTime(simpleDateFormat.parse(birthDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.sex = !sex.equals("male".toLowerCase());
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.nationality = nationality;
        this.crossOver = !crossOver.equals("no".toLowerCase());

        this.grade = educationGrade.valueOf(grade.toUpperCase());
        this.universityName = universityName;
        this.faculty = faculty;
        this.specialization = specialization;
        this.year = year;
        this.nativeLang = nativeLang;

        for (String lang : anotherLang.split(",")) {
            lang = lang.trim();
            addNewAnotherLang(lang);
        }

        this.exp = exp.equals("yes".toLowerCase());
        this.aboutDescription = aboutDescription;
    }

    public boolean addNewAnotherLang(String lang) {
        if (lang == null || lang.isEmpty())
            return false;

        anotherLang.add(lang);

        return true;
    }

    public String getAllAnotherLang() {
        String langList = "";

        for (String lang : anotherLang)
            langList += lang + ", ";

        langList = langList.trim();
        langList = langList.substring(0, langList.length() - 1);
        return langList;
    }

    @Override
    public boolean equals(Object anObject) {
        if (!(anObject instanceof Resume))
            return false;

        Resume object = (Resume) anObject;

        if (!fullName.equals(object.fullName)) return false;
        if (!birthDate.equals(object.birthDate)) return false;
        if (sex != object.sex) return false;
        if (!city.equals(object.city)) return false;
        if (!phoneNumber.equals(object.phoneNumber)) return false;
        if (!nationality.equals(object.nationality)) return false;
        if (crossOver != object.crossOver) return false;
        if (!grade.toString().equals(object.grade.toString())) return false;
        if (!universityName.equals(object.universityName)) return false;
        if (!faculty.equals(object.faculty)) return false;
        if (!specialization.equals(object.specialization)) return false;
        if (year != object.year) return false;
        if (!nativeLang.equals(object.nativeLang)) return false;
        if (!anotherLang.equals(object.anotherLang)) return false;
        if (exp != object.exp) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return fullName.length() +
                birthDate.get(Calendar.DAY_OF_WEEK) +
                birthDate.get(Calendar.MONTH) +
                birthDate.get(Calendar.YEAR) +
                (sex ? 1 : 0) +
                city.length() +
                phoneNumber.length() +
                nationality.length() +
                (crossOver ? 1 : 0) +
                +
                        universityName.length() +
                faculty.length() +
                specialization.length() +
                year +
                nativeLang.length() +
                anotherLang.size() +
                (exp ? 1 : 0) * 17;
    }

    @Override
    public String toString() {
        return "Резюме по умолчанию: \n" +
                "Полное имя: " + fullName +
                "\nДата рождения: " + birthDate.get(Calendar.DAY_OF_MONTH) + "." + birthDate.get(Calendar.MONTH) +
                "." + birthDate.get(Calendar.YEAR) +
                "\nПол: " + (sex ? "Женский" : "Мужской") +
                "\nГород проживания: " + city +
                "\nТелефон: " + phoneNumber +
                "\nГражданство: " + nationality +
                "\nГотов ли к переезду: " + (crossOver ? "Да" : "Нет") +
                "\nУровень образования: " + grade +
                "\nНаименование университета: " + universityName +
                "\nФакультет: " + faculty +
                "\nСпециализация: " + specialization +
                "\nГод окончания: " + year +
                "\nРодной язык : " + nativeLang +
                "\nДругие языки: " + getAllAnotherLang() +
                "\nОпыт: " + (exp ? "Есть" : "Нет");
    }

    public long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
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

    public boolean isCrossOver() {
        return crossOver;
    }

    public void setCrossOver(boolean crossOver) {
        this.crossOver = crossOver;
    }

    public educationGrade getGrade() {
        return grade;
    }

    public void setGrade(educationGrade grade) {
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getNativeLang() {
        return nativeLang;
    }

    public void setNativeLang(String nativeLang) {
        this.nativeLang = nativeLang;
    }

    public boolean isExp() {
        return exp;
    }

    public void setExp(boolean exp) {
        this.exp = exp;
    }

    public String getAboutDescription() {
        return aboutDescription;
    }

    public void setAboutDescription(String aboutDescription) {
        this.aboutDescription = aboutDescription;
    }
}
