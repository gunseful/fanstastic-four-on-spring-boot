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
        var order = orderDao.findByUserAndStatus(user, "not_ordered");
        var products = order.getProducts();
        var product = productDao.findById(id).orElse(null);
        products.put(product, products.get(product) + 1);
        order.setProducts(products);
        orderDao.save(order);
    }

    public void minusProduct(User user, int id) {
        var order = orderDao.findByUserAndStatus(user, "not_ordered");
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

    public List<Order> getOrdersByUser(User user){
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

    public void makePaid(int id){
        var order = orderDao.findById(id).orElseThrow(NullPointerException::new);
        order.setStatus("paid");
        orderDao.save(order);
    }

    public void deleteFromBasket(User user, int id){
        var order = orderDao.findByUserAndStatus(user, "not_ordered");
        Map<Product, Integer> products = order.getProducts();
        Product productForDelete = null;
        for (Product product : products.keySet()) {
            if (product.getId() == id) {
                productForDelete = product;
            }
        }
        products.remove(productForDelete);
        order.setProducts(products);
        orderDao.save(order);
    }

    public void addToUserBasket(User user, int id){
        if (orderDao.findByUserAndStatus(user, "not_ordered") == null) {
            Order order = new Order();
            order.setProducts(new HashMap<>());
            order.setUser(user);
            order.setStatus("not_ordered");
            order.setDate(java.sql.Date.valueOf(LocalDate.now()));
            orderDao.save(order);
        }
        var order = orderDao.findByUserAndStatus(user, "not_ordered");
        var products = order.getProducts();
        var product = productDao.findById(id).orElse(null);
        if (products.containsKey(product)) {
            products.put(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
        order.setProducts(products);
        orderDao.save(order);
    }

    public void deleteOrder(int id){
        var order = orderDao.findById(id).orElseThrow(NullPointerException::new);
        orderDao.delete(order);
    }

    public Order findByUserAndStatus(User user, String status) {
        return orderDao.findByUserAndStatus(user, status);
    }


    public void makeOrder(int id) {
        var order = orderDao.findById(id).orElseThrow(NullPointerException::new);
        order.setStatus("ordered");
        order.setDate(java.sql.Date.valueOf(LocalDate.now()));
        orderDao.save(order);
    }
}
