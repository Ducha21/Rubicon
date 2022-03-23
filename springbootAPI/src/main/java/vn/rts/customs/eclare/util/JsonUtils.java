/**
 *
 */
package vn.rts.customs.eclare.util;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ThanhNC - 2021-04-19
 *
 */
public class JsonUtils {

    private JsonUtils() {
        // not called
    }

    /**
     *
     * @param json
     * @param element = $.store.book[*].author, return: the authors of all books in
     *                the store element = $..author, return: all authors element =
     *                $.store.*, return: all things in store element =
     *                $.store..price, return: the price of everything in the store
     *                element = $..book[2], return: the third book element =
     *                $..book[(@.length-1)] or element = $..book[-1:], return: the
     *                last book in order. element = $..book[0,1] or element =
     *                $..book[:2], return: the first two books element =
     *                $..book[?(@.isbn)], return: filter all books with isbn number
     *                element = $..book[?(@.price<10)], return: filter all books
     *                cheapier than 10
     * @return
     */
    public static List<String> getListValueWithElement(String json, String element) {
        try {
            if (StringUtils.isEmpty(json) || StringUtils.isEmpty(element))
                return new ArrayList<>();
            return JsonPath.read(json, element);
        } catch (PathNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public static Object getValueWithElement(String json, String element) {
        try {
            if (StringUtils.isEmpty(json) || StringUtils.isEmpty(element))
                return null;
            return JsonPath.read(json, element);
        } catch (PathNotFoundException e) {
            return null;
        }
    }
}
