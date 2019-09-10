package fantasticfour.controllers.dao;

import fantasticfour.entity.Order;
import fantasticfour.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDao extends JpaRepository<Order, Integer> {
    Order findByUser(User user);
    Order findByUserAndStatus(User user, String status);
    List<Order> findAllByUserAndStatus(User user, String status);

}
