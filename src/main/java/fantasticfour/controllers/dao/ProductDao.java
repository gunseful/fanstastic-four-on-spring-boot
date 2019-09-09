package fantasticfour.controllers.dao;

import fantasticfour.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductDao extends CrudRepository<Product, Long> {
}
