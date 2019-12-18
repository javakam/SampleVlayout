package sample.vlayout.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Title:GsonUtils
 * <p>
 * Description:
 * </p>
 *
 * @author Changbao
 * @date 2019/12/17 10:13
 */
public class GsonUtils {

    private static final Gson sGson = createGson();

    private static Gson createGson() {
        return new GsonBuilder().serializeNulls().create();
    }

    /**
     * 将对象转成jsonString
     */
    public static <T> String toJsonString(T t) {
        Gson gson = createGson();
        return gson.toJson(t).toString();
    }

    public static JSONObject toJson(Object obj) throws JSONException {
        String str = sGson.toJson(obj);
        return new JSONObject(str);
    }

    /**
     * 将json字符串转成对象
     */
    public static <T> T fromJson(String json, Class<T> cla) throws JsonSyntaxException {
        return sGson.fromJson(json, cla);
    }

    /**
     * new TypeToken<List<Music>>(){}.getType()
     */
    public static <T> List<T> fromJson(String jsonStr, Type type) throws JsonSyntaxException {
        return sGson.fromJson(jsonStr, type);
    }

}