package fantasticfour.controllers.service;

import fantasticfour.controllers.dao.OrderDao;
import fantasticfour.controllers.dao.ProductDao;
import fantasticfour.entity.Order;
import fantasticfour.entity.Product;
import fantasticfour.entity.Role;
import fantasticfour.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class OrderService {

    private final OrderDao orderDao;
    private final ProductDao productDao;

    public OrderService(OrderDao orderDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
    }

    public void plusProduct(User user, int id) {
        var order = orderDao.findByUserAndStatus(user, "not_ordered").orElseThrow(NullPointerException::new);
        var products = order.getProducts();
        var product = productDao.findById(id).orElseThrow(NullPointerException::new);
        products.put(product, products.get(product) + 1);
        order.setProducts(products);
        orderDao.save(order);
    }

    public void minusProduct(User user, int id) {
        var order = orderDao.findByUserAndStatus(user, "not_ordered").orElseThrow(NullPointerException::new);
        var products = order.getProducts();
        var product = productDao.findById(id).orElse(null);
        if (products.get(product) > 1) {
            products.put(product, products.get(product) - 1);
        } else {
            products.remove(product);
        }
        order.setProducts(products);
        orderDao.save(order);
    }

    public List<Order> getOrdersByUser(User user) {
        ArrayList<Order> list;
        if (user.getRoles().contains(Role.ADMIN)) {
            list = (ArrayList<Order>) orderDao.findAllByStatus("ordered");
            list.addAll(orderDao.findAllByStatus("paid"));
        } else {
            list = (ArrayList<Order>) orderDao.findAllByUserAndStatus(user, "ordered");
            list.addAll(orderDao.findAllByUserAndStatus(user, "paid"));
        }
        list.sort(Comparator.comparingLong(Order::getId));
        return list;
    }

    public void makePaid(int id) {
        var order = orderDao.findById(id).orElseThrow(NullPointerException::new);
        order.setStatus("paid");
        orderDao.save(order);
    }

    public void deleteFromBasket(User user, int id) {
        var order = orderDao.findByUserAndStatus(user, "not_ordered").orElseThrow(NullPointerException::new);
        var products = order.getProducts();
        Product productForDelete = products.keySet()
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst().orElseThrow(NullPointerException::new);
        products.remove(productForDelete);
        order.setProducts(products);
        orderDao.save(order);
    }

    public void addToUserBasket(User user, int id) {
        Order order = orderDao.findByUserAndStatus(user, "not_ordered").orElseGet(() ->
                {
                    Order newOrder = new Order();
                    newOrder.setProducts(new HashMap<>());
                    newOrder.setUser(user);
                    newOrder.setStatus("not_ordered");
                    newOrder.setDate(java.sql.Date.valueOf(LocalDate.now()));
                    return newOrder;
                }
        );
        var products = order.getProducts();
        var product = productDao.findById(id).orElseThrow(NullPointerException::new);
        if (products.containsKey(product)) {
            products.put(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
        order.setProducts(products);
        orderDao.save(order);
}

    public void deleteOrder(int id) {
        var order = orderDao.findById(id).orElseThrow(NullPointerException::new);
        orderDao.delete(order);
    }

    public Order findByUserAndStatus(User user, String status) {
        return orderDao.findByUserAndStatus(user, status).orElseThrow(NullPointerException::new);
    }


    public void makeOrder(int id) {
        var order = orderDao.findById(id).orElseThrow(NullPointerException::new);
        order.setStatus("ordered");
        order.setDate(java.sql.Date.valueOf(LocalDate.now()));
        orderDao.save(order);
    }
}
