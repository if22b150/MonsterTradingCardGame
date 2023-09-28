package at.technikum.repositories.user;

import at.technikum.models.User;

import java.util.ArrayList;

public interface IRepository<T> {
    ArrayList<T> all();

    T get(int id);

    T create();
}
