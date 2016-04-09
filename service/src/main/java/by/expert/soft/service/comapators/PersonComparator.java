package by.expert.soft.service.comapators;

import by.expert.soft.vo.PersonVO;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public final class PersonComparator {

    private static Map<String, Comparator> comparators = new HashMap<>();

    public static Comparator loadComparator(String columnName) {
        return comparators.get(columnName);
    }

    private PersonComparator() {
    }

    static {
        comparators.put("name", createNameComparator());
        comparators.put("surname", createSurnameComparator());
        comparators.put("email", createEmailComparator());
        comparators.put("login", createLoginComparator());
        comparators.put("phoneNumber", createPhoneComparator());
    }

    private static Comparator createPhoneComparator() {
        return new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                PersonVO person1 = (PersonVO) o1;
                PersonVO person2 = (PersonVO) o2;
                return person1.getPhoneNumber().compareTo(person2.getPhoneNumber());
            }
        };
    }

    private static Comparator createLoginComparator() {
        return new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                PersonVO person1 = (PersonVO) o1;
                PersonVO person2 = (PersonVO) o2;
                return person1.getLogin().compareTo(person2.getLogin());
            }
        };
    }

    private static Comparator createEmailComparator() {
        return new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                PersonVO person1 = (PersonVO) o1;
                PersonVO person2 = (PersonVO) o2;
                return person1.getEmail().compareTo(person2.getEmail());
            }
        };
    }

    private static Comparator createSurnameComparator() {
        return new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                PersonVO person1 = (PersonVO) o1;
                PersonVO person2 = (PersonVO) o2;
                return person1.getSurname().compareTo(person2.getSurname());
            }
        };
    }

    private static Comparator createNameComparator() {
        return new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                PersonVO person1 = (PersonVO) o1;
                PersonVO person2 = (PersonVO) o2;
                return person1.getName().compareTo(person2.getName());
            }
        };
    }
}
