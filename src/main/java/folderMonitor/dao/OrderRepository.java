package folderMonitor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import folderMonitor.domain.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, String> {

}
