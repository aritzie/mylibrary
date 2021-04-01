package com.sanvalero.mylibrary.repository;

import com.sanvalero.mylibrary.domain.Editorial;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface EditorialRepository extends CrudRepository<Editorial, Long> {

    Set<Editorial> findAll();
    Optional<Editorial> findByName(String name);
}
