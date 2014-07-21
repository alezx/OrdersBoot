package folderMonitor.reader.order;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Component;

import ale.HandshakeToOrders.JsonOrder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import folderMonitor.reader.Reader;

@Component(value = "ordersJsonReader")
public class OrdersJsonReader implements Reader {

	@Override
	public Object read(File f) throws Exception {
		ObjectMapper o = new ObjectMapper();
		List<JsonOrder> jsonOrders = o.readValue(f, new TypeReference<List<JsonOrder>>() {
		});
		return jsonOrders;
	}

}
