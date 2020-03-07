package org.panda.demo.flowable.springboot.repository;

import org.panda.demo.flowable.springboot.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByUsername(String username);
}