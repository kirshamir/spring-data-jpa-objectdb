package com.example.firstjpademo;

import com.example.firstjpademo.model.Person;
import static com.example.firstjpademo.model.Person.Gender;

import com.example.firstjpademo.model.PersonInfo;
import com.example.firstjpademo.model.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

@SpringBootApplication
public class FirstJpaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstJpaDemoApplication.class, args);
	}

	@Bean(name="entityManagerFactory")
	public EntityManagerFactory getEntityManagerFactoryBean() {
		return Persistence.createEntityManagerFactory("spring-data-jpa-test.odb");
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		return txManager;
	}

	@Bean
	@ConfigurationProperties("app.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public CommandLineRunner runner(PersonRepository repository) {
		return (args) -> {
			repository.save(new Person("Or", LocalDate.of(1980, 10, 10), Person.Gender.MALE));
			repository.save(new Person("Dora", LocalDate.of(1976, 3, 5), Gender.FEMALE));
			repository.save(new Person("Miriam", LocalDate.of(1996, 12, 21), Gender.FEMALE));
			repository.save(new Person("Momo", LocalDate.of(1998, 9, 12), Gender.MALE));
			// fetch all persons
			System.out.println("findAll():");
			for (Person p : repository.findAll()) {
				System.out.println(p);
			}

            System.out.println("findAll born between 1980 and 2000:");
            for (Person p : repository.findAllByBirthDateBetween(
					Date.from(LocalDate.of(1980,1,1).atStartOfDay(ZoneId.systemDefault()).toInstant()),
					Date.from(LocalDate.of(2000,12,31).atStartOfDay(ZoneId.systemDefault()).toInstant())
            )) {
                System.out.println(p);
            }

            System.out.println("findAll born on December:");
            for (Person p : repository.findAllByBirthDateMonth(12)) {
                System.out.println(p);
            }

            System.out.println("findAll born in 1998:");
            for (Person p : repository.findAllByBirthDateYear(1998)) {
                System.out.println(p);
            }

			System.out.println("findAll Female:");
			for (Person p : repository.findAllByGender(Gender.FEMALE)) {
				System.out.println(p);
			}

			System.out.println("findAll Female born between 1980-2000:");
			for (Person p : repository.findAllByBirthDateBetweenAndGender(
					Date.from(LocalDate.of(1980,1,1).atStartOfDay(ZoneId.systemDefault()).toInstant()),
					Date.from(LocalDate.of(2000,12,31).atStartOfDay(ZoneId.systemDefault()).toInstant()),
					Gender.FEMALE
			)) {
				System.out.println(p);
			}

			System.out.println("findAll names:");
			for (String name : repository.findAllNames()) {
				System.out.println(name);
			}

			System.out.println("findAll names and ids:");
			for (PersonInfo p : repository.findAllNamesAndIds()) {
				System.out.println(p);
			}

			System.out.println("findAll names and ids by gender:");
			for (PersonInfo p : repository.findAllPersonInfoByGender(Gender.MALE)) {
				System.out.println(p);
			}
		};
	}
}
