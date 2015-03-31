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
@NamedQueries({
        @NamedQuery(name = Resume.FIND_BY_FULL_NAME, query = "SELECT r FROM Resume r WHERE r.fullName LIKE :fullName"),
        @NamedQuery(name = Resume.FIND_RESUME_BY_LOGIN, query = "SELECT r FROM Resume r WHERE r.login = :login"),
        @NamedQuery(name = Resume.FIND_RESUME_BY_ID, query = "SELECT r FROM Resume r WHERE r.id = :id"),
        @NamedQuery(name = Resume.FIND_RESUME_BY_CITY, query = "SELECT r FROM Resume r WHERE r.city LIKE :city"),
})
public class Resume implements java.io.Serializable {

    public static final String FIND_BY_FULL_NAME = "Resume.findByFullName";
    public static final String FIND_RESUME_BY_CITY = "Resume.findResumeByCity";
    public static final String FIND_RESUME_BY_LOGIN = "Resume.findResumeByLogin";
    public static final String FIND_RESUME_BY_ID = "Resume.findResumeById";

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "login", length = 50)
    private String login;

    @Column(name = "fullName", length = 100)
    private String fullName;            // Полное имя

    @Column(name = "birthDate", length = 12)
    private Calendar birthDate;         // Дата рождения

    @Column(name = "sex", length = 2)
    private boolean sex;                // Пол (false - м, true - ж)

    @Column(name = "city", length = 50)
    private String city;                // Город проживания

    @Column(name = "phoneNumber", length = 15)
    private String phoneNumber;         // Номер телефона

    @Column(name = "nationality", length = 25)
    private String nationality;         // Гражданство

    @Column(name = "crossOver", length = 2)
    private boolean crossOver;          // Возможен ли переезд

    @Column(name = "grade", length = 10)
    private educationGrade grade;       // Уровень обраования

    @Column(name = "universityName", length = 50)
    private String universityName;      // Имя университета

    @Column(name = "faculty", length = 50)
    private String faculty;             // Факультет

    @Column(name = "specialization", length = 50)
    private String specialization;      // Специализация

    @Column(name = "year", length = 4)
    private int year;                   // Год окончания

    @Column(name = "nativeLang", length = 10)
    private String nativeLang;            // Родной язык

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> anotherLang = new ArrayList<String>();           // Другой язык (владение)

    @Column(name = "exp", length = 2)
    private boolean exp;                // Опыт работы

    @Column(name = "aboutDescription")
    private String aboutDescription;

    public Resume() {
    }

    public Resume(String login, String fullName, String birthDate, String sex, String city, String phoneNumber, String nationality,
                  String crossOver, String grade, String universityName, String faculty, String specialization, int year,
                  String nativeLang, String anotherLang, String exp, String aboutDescription) {
        this.login = login;
        this.fullName = fullName;
        this.birthDate = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
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

        System.out.println(this);
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

    public String getStringId() {
        return String.valueOf(id);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String quickInfo() {
        return "Краткая информация:<br>" +
                "Город проживания: " + getCity() + " " +
                "Контактный телефон: " + getPhoneNumber() + " " +
                "Образование: " + getGrade();
    }
}
