package application.service;

import application.repository.AuthorRepository;

import java.io.*;

public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors(File file) throws IOException {
        BufferedReader bfr = new BufferedReader(new FileReader(file));

        String line;
        String[] tokens;
        String firstName;
        String lastName;
        while ((line = bfr.readLine()) != null) {
            //use regex and the Pattern and Matcher classes from java.util.regex;
        }
    }
}
