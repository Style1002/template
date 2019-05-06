package com.gouge.template.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author GouGe
 * @data 2019/5/6 17:15
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Student {
    private String name;
    private Integer age;
    private Long salary;

    static Student merge(Student s1, Student s2) {
        if (!s1.equals(s2)) {
            throw new IllegalArgumentException();
        }
        return new Student(s1.name, (s1.getAge() + s1.getAge()) / 2, s1.salary + s2.salary);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Student other = (Student) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    public Student(String name, Long salary) {
        this.name = name;
        this.salary = salary;
    }
}
