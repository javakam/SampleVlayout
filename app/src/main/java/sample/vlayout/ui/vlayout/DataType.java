package sample.vlayout.ui.vlayout;

/**
 * Title: DataType
 * <pre>
 * Description:列表显示模式
 *
 *      1.  viewType   列表样式(用于前端辨识)
 *      2.  dataType   媒体类型(标明数据类型)
 *              -1混合模式,适用于多图;
 *              0默认类型;1视频;2音频;3音视频;
 *              4广告WebView展示;5广告其它APP;6跳转到其他页面;7待定
 *
 * </pre>
 *
 * @author Changbao
 * @date 2019/12/19  9:52
 */
public interface DataType {

    int MIXED = -1;
    int DEFAULT = 0;
    int VIDEO = 1;
    int AUDIO = 2;
    int VIDEO_AUDIO = 3;
    int WEBVIEW = 4;
    int OTHER_APP = 5;
    int OTHER_PAGE = 6;


}