package folderMonitor.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import folderMonitor.domain.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

	List<Order> findByCodeAndCustomer(String code, String customer);
}
