package fantasticfour.controllers.dao;

import fantasticfour.entity.Order;
import fantasticfour.entity.Product;
import fantasticfour.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderDao extends JpaRepository<Order, Integer> {
    Optional<Order> findByUserAndStatus(User user, String status);
    List<Order> findAllByUserAndStatus(User user, String status);
    List<Order> findAllByStatus(String status);

}
