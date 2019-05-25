package com.zvezdomirov.bookshopsystem.repositories;

import com.zvezdomirov.bookshopsystem.modules.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository
        extends CrudRepository<Author, Long> {
    boolean existsAuthorById(long id);
}
