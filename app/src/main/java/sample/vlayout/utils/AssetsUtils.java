package sample.vlayout.utils;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Title: AssetsUtils
 * <p>
 * Description: 读取 main -> assets 目录下的文件
 * <pre>
 *     eg: AssetsUtils.getBeanByClass(Application.get(), "video.json", VideoBean.class);
 * </pre>
 *
 * @author Changbao
 * @date 2019/11/7  10:12
 */
public class AssetsUtils {

    public static String getJson(@NonNull Context context, @NonNull String fileName) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static <T extends Object> T getBeanByClass(@NonNull Context context, @NonNull String fileName, @NonNull Class<T> clz) {
        return new Gson().fromJson(getJson(context, fileName), clz);
    }

    public static <T extends Object> List<T> getBeanByType(@NonNull Context context, @NonNull String fileName, @NonNull Type type) {
        return new Gson().fromJson(getJson(context, fileName), type);
    }

}