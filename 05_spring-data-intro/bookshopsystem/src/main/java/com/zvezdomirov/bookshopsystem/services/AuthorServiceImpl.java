package com.zvezdomirov.bookshopsystem.services;

import com.zvezdomirov.bookshopsystem.modules.Author;
import com.zvezdomirov.bookshopsystem.repositories.AuthorRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void registerAuthor(Author author) {
        long authorId = author.getId();
        if (!authorRepository.existsAuthorById(authorId)) {
            authorRepository.save(author);
        }
    }
}
