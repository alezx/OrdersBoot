package folderMonitor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import folderMonitor.domain.OrderEntry;

@Repository
public interface OrderEntryRepository extends CrudRepository<OrderEntry, Integer> {

}
