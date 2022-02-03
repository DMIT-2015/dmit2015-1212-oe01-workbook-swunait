package dmit2015.repository;

import dmit2015.entity.TodoItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional      // Every method in class requires a new transaction
public class TodoItemRepository {

    @PersistenceContext
    private EntityManager em;

    public void add(TodoItem newTodoItem) {
        em.persist(newTodoItem);
    }

    public void update(TodoItem updatedTodoItem) {
        Optional<TodoItem> optionalTodoItem = findById(updatedTodoItem.getId());
        if (optionalTodoItem.isPresent()) {
            TodoItem existingTodoItem = optionalTodoItem.get();
            existingTodoItem.setName(updatedTodoItem.getName());
            existingTodoItem.setComplete(updatedTodoItem.isComplete());
            em.merge(existingTodoItem);
            em.flush();
        }
    }

    public void remove(Long id) {
        Optional<TodoItem> optionalTodoItem = findById(id);
        if (optionalTodoItem.isPresent()) {
            TodoItem existingTodoItem = optionalTodoItem.get();
            em.remove(existingTodoItem);
            em.flush();
        }
    }

    public Optional<TodoItem> findById(Long id) {
        Optional<TodoItem> optionalTodoItem = Optional.empty();
        try {
            TodoItem querySingleResult = em.find(TodoItem.class, id);
            if (querySingleResult != null) {
                optionalTodoItem = Optional.of(querySingleResult);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return optionalTodoItem;
    }

    public List<TodoItem> findAll() {
        return em.createQuery(
                "SELECT ti FROM TodoItem ti"
                , TodoItem.class)
                .getResultList();
    }

}

