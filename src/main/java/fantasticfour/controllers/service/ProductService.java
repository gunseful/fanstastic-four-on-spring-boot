package fantasticfour.controllers.service;

import fantasticfour.controllers.dao.OrderDao;
import fantasticfour.controllers.dao.ProductDao;
import fantasticfour.entity.Order;
import fantasticfour.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;
    private final OrderDao orderDao;


    public ProductService(ProductDao productDao, OrderDao orderDao) {
        this.productDao = productDao;
        this.orderDao = orderDao;
    }

    public Iterable<Product> findAll() {
        return productDao.findAll();
    }

    public void save(Product product) {
        productDao.save(product);
    }

    public void deleteProduct(int id){
        var product = productDao.findById(id).orElseThrow(NullPointerException::new);
        var orders =  orderDao.findAll();
        for(Order order : orders){
            if(order.getProducts().containsKey(product)){
                order.getProducts().remove(product);
                orderDao.save(order);
            }
        }
        productDao.deleteById(id);
    }
}
