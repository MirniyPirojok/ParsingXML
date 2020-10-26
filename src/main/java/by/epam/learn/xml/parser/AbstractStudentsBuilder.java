package by.epam.learn.xml.parser;

import by.epam.learn.xml.entity.Student;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractStudentsBuilder {
    protected Set<Student> students;

    public AbstractStudentsBuilder() {
        students = new HashSet<>();
    }

    public AbstractStudentsBuilder(Set<Student> students) {
        this.students = students;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public abstract void buildSetStudents(String fileName);
}
