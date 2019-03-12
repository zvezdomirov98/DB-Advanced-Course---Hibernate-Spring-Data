package com.zvezdomirov.bookshopsystem.repositories;

import com.zvezdomirov.bookshopsystem.modules.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository
        extends CrudRepository<Category, Long> {
}
