package at.technikum.repositories.session;

import at.technikum.models.Session;

public interface ISessionRepository {
    Session create(int userId, String token);
    Session getByUser(int userId);
}
