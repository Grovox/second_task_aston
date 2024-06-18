package repo;

public interface Repository<E> {
    void save(E e);

    void update(E e);

    void delete(E e);
}
