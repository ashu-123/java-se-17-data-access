package com.learning.repository;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> findById(long id);

    List<T> findAll();
}
