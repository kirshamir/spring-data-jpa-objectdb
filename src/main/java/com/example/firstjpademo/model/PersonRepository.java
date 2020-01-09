package com.example.firstjpademo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface PersonRepository
        extends JpaRepository<Person, Long> {

    List<Person> findAllByBirthDateBetween(Date d1, Date d2);

    List<Person> findAllByGender(Person.Gender g);

    List<Person> findAllByBirthDateBetweenAndGender(Date d1, Date d2, Person.Gender gender);

    // see: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

    @Query("Select p from Person p WHERE MONTH(p.birthDate) = :month")
    List<Person> findAllByBirthDateMonth(int month);

    @Query("Select p from Person p WHERE YEAR(p.birthDate) = :year")
    List<Person> findAllByBirthDateYear(int year);

}
