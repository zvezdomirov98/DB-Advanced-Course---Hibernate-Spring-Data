package com.zvezdomirov.bookshopsystem;

import com.zvezdomirov.bookshopsystem.modules.Author;
import com.zvezdomirov.bookshopsystem.modules.Book;
import com.zvezdomirov.bookshopsystem.services.AuthorServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@SpringBootApplication
public class ConsoleRunner implements CommandLineRunner {
    private AuthorServiceImpl authorService;

    public ConsoleRunner(AuthorServiceImpl authorService) {
        this.authorService = authorService;
    }

    @Override
    public void run(String... args) throws Exception {
        
    }
}
