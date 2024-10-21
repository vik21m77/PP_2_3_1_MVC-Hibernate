package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        return em.createNativeQuery(sql, User.class).getResultList();
    }

    @Override
    @Transactional
    public void saveOrUpdateUser(User user) {
        if (user.getId() == null) {
            // Если у объекта нет ID, это новый объект, сохраняем его
            em.persist(user);

        } else {
            // Если у объекта есть ID, используем merge для обновления или вставки
            em.merge(user);
        }
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        em.remove(
                em.find(User.class, id)
        );
    }

    @Override
    @Transactional
    public User getUser(long id) {
        return em.find(User.class, id);
    }
}
