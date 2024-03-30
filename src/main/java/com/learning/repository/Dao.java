package com.learning.repository;

import java.util.List;

public interface Dao<T> {

    List<T> findAll();
}
