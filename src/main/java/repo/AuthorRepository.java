package repo;

import model.Author;

import java.util.List;

public interface AuthorRepository extends Repository<Author> {
    List<Author> getAll();

    Author findById(int id);

    void deleteAuthorByIdIfNotHaveBooks(List<Integer> authorsId);
}
