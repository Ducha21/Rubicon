package vn.rts.customs.eclare.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.BeanUtils;

public class MapperUtils {
	public static <U, V> List<V> mappingList(List<U> source, Supplier<V> supplier) {
		List<V> listV = new ArrayList<>();
		for (U u : source) {
			V v = supplier.get();
			BeanUtils.copyProperties(u, v);
			listV.add(v);
		}
		return listV;
	}
}
