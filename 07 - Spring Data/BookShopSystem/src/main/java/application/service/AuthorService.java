package application.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface AuthorService {
    void seedAuthors(File file) throws IOException;
}
