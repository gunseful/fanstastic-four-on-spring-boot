package fantasticfour.controllers.service;

import fantasticfour.controllers.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderService {
    @Autowired
    private OrderDao orderDao;
}
