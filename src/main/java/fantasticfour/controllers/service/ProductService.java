package fantasticfour.controllers.service;

import fantasticfour.controllers.dao.OrderDao;
import fantasticfour.controllers.dao.ProductDao;
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
        orderDao.findAll().forEach(p -> p.getProducts().keySet().stream().filter(o -> o.getId()==id).forEach(o -> p.getProducts().remove(o)));
        productDao.deleteById(id);
    }
}
