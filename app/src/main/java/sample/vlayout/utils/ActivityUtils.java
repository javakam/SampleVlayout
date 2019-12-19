package sample.vlayout.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Title:ActivityUtils
 * <p>
 * Description:Activity 工具类
 * </p>
 *
 * @author Changbao
 * @date 2019/11/15 14:44
 */
public class ActivityUtils {

    public static boolean isActivityLive(Activity activity) {
        return activity != null && !activity.isFinishing() && !activity.isDestroyed();
    }

    /**
     * 获取 Application
     *
     * @return application
     */
    @SuppressWarnings("RedundantArrayCreation")
    @SuppressLint("PrivateApi")
    public static Application getApplication() {
        Application application = null;
        Class<?> activityThreadClass;
        try {
            activityThreadClass = Class.forName("android.app.ActivityThread");
            final Method method2 = activityThreadClass.getMethod("currentActivityThread", new Class[0]);
            // 得到当前的ActivityThread对象
            Object localObject = method2.invoke(null, (Object[]) null);

            final Method method = activityThreadClass
                    .getMethod("getApplication");
            application = (Application) method.invoke(localObject, (Object[]) null);

        } catch (ClassNotFoundException | IllegalArgumentException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return application;
    }

    /**
     * 切换全屏状态
     *
     * @param activity Activity
     * @param isFull   设置为true则全屏，否则非全屏
     */
    public static void toggleFullScreen(@NonNull Activity activity, boolean isFull) {
        hideTitleBar(activity);
        Window window = activity.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        if (isFull) {
            params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            window.setAttributes(params);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.setAttributes(params);
            window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * 设置为全屏
     *
     * @param activity Activity
     */
    public static void setFullScreen(@NonNull Activity activity) {
        toggleFullScreen(activity, true);
    }

    /**
     * 隐藏Activity的系统默认标题栏
     *
     * @param activity Activity
     */
    public static void hideTitleBar(@NonNull Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 强制设置Activity的显示方向为垂直方向。
     *
     * @param activity Activity
     */
    public static void setScreenVertical(@NonNull Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 强制设置Activity的显示方向为横向
     *
     * @param activity Activity
     */
    public static void setScreenHorizontal(@NonNull Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 隐藏软件输入法
     *
     * @param activity Activity
     */
    public static void hideSoftInput(@NonNull Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * 使UI适配输入法
     *
     * @param activity Activity
     */
    public static void adjustSoftInput(@NonNull Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    /**
     * 快捷方式是否存在
     */
    public static boolean ifAddShortCut(Context context, String appName) {
        boolean isInstallShortCut = false;
        ContentResolver cr = context.getContentResolver();
        String authority = "com.android.launcher2.settings";
        Cursor cursor = null;
        try {
            Uri uri = Uri.parse("content://" + authority + "/favorites?notify=true");
            cursor = cr.query(uri,
                    new String[]{"title", "iconResource"},
                    "title=?",
                    new String[]{appName}, null);
            if (null != cursor && cursor.getCount() > 0) {
                isInstallShortCut = true;
            }
        } catch (Exception e) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return isInstallShortCut;
    }

    /**
     * 获取系统状态栏高度
     *
     * @param activity Activity
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(@NonNull Activity activity) {
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        return localRect.top;
    }

}