package sample.vlayout.ui.vlayout.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Title: VideoListEntity
 * <p>
 * Description:
 * </p>
 *
 * @author Changbao
 * @date 2019/12/18  9:24
 */
public class VideoListEntity implements Serializable {


    /**
     * data : [{"id":123,"viewType":1,"title":"热点聚焦","more":true,"icon":""},{"id":123,"viewType":4,"title":"热点聚焦","showNum":1,"content":[{"id":123,"title":"绿卡能成功登陆新三板","mediaId":"440a4e0dd61e443696bf1542020790fb","mediaName":"","summary":"文件可以从文件管理器中选择（多个文件可以选择），并拖入UltraEdit的窗口。如果鼠标按钮被释放超过UltraEdit中选择的文件将自动打开鼠标用UltraEdit编辑。","dataType":1,"createtime":1555663308,"updatetime":1555663308,"commentNum":"1865","praiseNum":"98","shareNum":"786","authorId":"1212212","authorName":"逗你玩","url":"","categoryname":"新三板123","audio":"","video":{"FD":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-24511cd56568c9dd72748c8fcf969373-fd.mp4","LD":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-901b338aaa1660de829114909d6d7ae0-ld.mp4","SD":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-562c47b777a979b643ac8aaf0b35f5dd-sd.mp4","HD":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-735fb9abede476e4feb1d74eddd2112f-hd.mp4","OD":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-c4c94e7f4918122b433281461812ea8d-od-S00000001-200000.mp4","K2":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-dc40c6992b38fb9fd8654c9bec79b3ed-2k.mp4"},"cover":"http://vod.zqrb.cn/image/cover/39ECDE4DE91E49109F6270B569E1D8C5-6-2.png","coverBig":"http://vod.zqrb.cn/image/cover/39ECDE4DE91E49109F6270B569E1D8C5-6-2.png"}]},{"id":123,"viewType":1,"title":"高端访谈","more":false,"icon":""},{"id":123,"viewType":5,"title":"高端访谈","showNum":3,"content":[{"title":"绿卡能成功登陆新三板","mediaId":"440a4e0dd61e443696bf1542020790fb","mediaName":"","summary":"文件可以从文件管理器中选择（多个文件可以选择），并拖入UltraEdit的窗口。如果鼠标按钮被释放超过UltraEdit中选择的文件将自动打开鼠标用UltraEdit编辑。","dataType":1,"createtime":1555663308,"updatetime":1555663308,"commentNum":"1865","praiseNum":"98","shareNum":"786","authorId":"1212212","authorName":"逗你玩","url":"","categoryname":"新三板123","audio":"","video":{"FD":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-24511cd56568c9dd72748c8fcf969373-fd.mp4","LD":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-901b338aaa1660de829114909d6d7ae0-ld.mp4","SD":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-562c47b777a979b643ac8aaf0b35f5dd-sd.mp4","HD":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-735fb9abede476e4feb1d74eddd2112f-hd.mp4","OD":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-c4c94e7f4918122b433281461812ea8d-od-S00000001-200000.mp4","K2":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-dc40c6992b38fb9fd8654c9bec79b3ed-2k.mp4"},"cover":"http://vod.zqrb.cn/image/cover/39ECDE4DE91E49109F6270B569E1D8C5-6-2.png","coverBig":"http://vod.zqrb.cn/image/cover/39ECDE4DE91E49109F6270B569E1D8C5-6-2.png"},{"title":"里华机械挂牌新三板","mediaId":"02c010748072406081ccf26c5cef4a61","mediaName":"","summary":"文件可以从文件管理器中选择（多个文件可以选择），并拖入UltraEdit的窗口。如果鼠标按钮被释放超过UltraEdit中选择的文件将自动打开鼠标用UltraEdit编辑。","dataType":1,"createtime":1555663308,"updatetime":1555663308,"commentNum":"1865","praiseNum":"98","shareNum":"786","authorId":"1212212","authorName":"逗你玩","categoryname":"新三板123","url":"","video":{"FD":"http://vod.zqrb.cn/02c010748072406081ccf26c5cef4a61/28263f523be8440e9681ada083d2c3c0-6b858a46b6e1c62d801dcdb1aaec3162-fd.mp4","LD":"http://vod.zqrb.cn/02c010748072406081ccf26c5cef4a61/28263f523be8440e9681ada083d2c3c0-ad6796bbf421287655e2c8dd2bae4e39-ld.mp4","SD":"http://vod.zqrb.cn/02c010748072406081ccf26c5cef4a61/28263f523be8440e9681ada083d2c3c0-538530f02ac2a048321d0b0da95742e9-sd.mp4","HD":"http://vod.zqrb.cn/02c010748072406081ccf26c5cef4a61/28263f523be8440e9681ada083d2c3c0-a36b52fe09e413475c3d003f31cdb300-hd.mp4","OD":"http://vod.zqrb.cn/02c010748072406081ccf26c5cef4a61/28263f523be8440e9681ada083d2c3c0-20dcd32ff3c62d1d282659dbf90f4c25-od-S00000001-200000.mp4","K2":"http://vod.zqrb.cn/02c010748072406081ccf26c5cef4a61/28263f523be8440e9681ada083d2c3c0-a4d567bc2b5b4d5993c6c50dd8577df0-2k.mp4"},"cover":"http://vod.zqrb.cn/image/cover/816DCF7ECD184E849043C2DF22D00B76-6-2.png","coverBig":"http://vod.zqrb.cn/image/cover/816DCF7ECD184E849043C2DF22D00B76-6-2.png"},{"id":123,"title":"总经理助理金小山","mediaId":"157e21f555bd4f2b98941fd99626c70f","mediaName":"","summary":"文件可以从文件管理器中选择（多个文件可以选择），并拖入UltraEdit的窗口。如果鼠标按钮被释放超过UltraEdit中选择的文件将自动打开鼠标用UltraEdit编辑。","dataType":1,"createtime":1555663308,"updatetime":1555663308,"commentNum":"1865","praiseNum":"98","shareNum":"786","authorId":"1212212","authorName":"逗你玩","categoryname":"新三板123","url":"","audio":"","video":{"LD":"http://vod.zqrb.cn/157e21f555bd4f2b98941fd99626c70f/f9460215e43c41f39e5ac635b3388851-2e5db5ec4cfa744f1cc4def9069052cf-ld.mp4","SD":"http://vod.zqrb.cn/157e21f555bd4f2b98941fd99626c70f/f9460215e43c41f39e5ac635b3388851-8a2d66e250aa0aa61bfea7e4ce3f4461-sd.mp4","HD":"http://vod.zqrb.cn/157e21f555bd4f2b98941fd99626c70f/f9460215e43c41f39e5ac635b3388851-4c4d99be9d999f92c7be00132a82b657-hd.mp4"},"cover":"http://vod.zqrb.cn/157e21f555bd4f2b98941fd99626c70f/snapshots/312c9177bc9b454c88663cc1ef2716fb-00004.jpg","coverBig":"http://vod.zqrb.cn/157e21f555bd4f2b98941fd99626c70f/snapshots/312c9177bc9b454c88663cc1ef2716fb-00004.jpg"}]},{"id":123,"viewType":1,"title":"高端访谈(非视频)","more":true,"icon":""},{"id":123,"viewType":5,"title":"高端访谈(非视频)","showNum":3,"content":[{"id":123,"title":"绿卡能成功登陆新三板","mediaId":"440a4e0dd61e443696bf1542020790fb","mediaName":"","dataType":0,"summary":"文件可以从文件管理器中选择（多个文件可以选择），并拖入UltraEdit的窗口。如果鼠标按钮被释放超过UltraEdit中选择的文件将自动打开鼠标用UltraEdit编辑。","createtime":1555663308,"updatetime":1555663308,"commentNum":"1865","praiseNum":"98","shareNum":"786","authorId":"1212212","authorName":"逗你玩","categoryname":"新三板123","url":"","cover":"http://vod.zqrb.cn/image/cover/39ECDE4DE91E49109F6270B569E1D8C5-6-2.png"},{"id":123,"title":"里华机械挂牌新三板","mediaId":"02c010748072406081ccf26c5cef4a61","mediaName":"","dataType":0,"summary":"文件可以从文件管理器中选择（多个文件可以选择），并拖入UltraEdit的窗口。如果鼠标按钮被释放超过UltraEdit中选择的文件将自动打开鼠标用UltraEdit编辑。","createtime":1555663308,"updatetime":1555663308,"commentNum":"1865","praiseNum":"98","shareNum":"786","authorId":"1212212","authorName":"逗你玩","categoryname":"新三板123","url":"","cover":"http://vod.zqrb.cn/image/cover/816DCF7ECD184E849043C2DF22D00B76-6-2.png"},{"id":123,"title":"总经理助理金小山","mediaId":"157e21f555bd4f2b98941fd99626c70f","mediaName":"","dataType":0,"summary":"文件可以从文件管理器中选择（多个文件可以选择），并拖入UltraEdit的窗口。如果鼠标按钮被释放超过UltraEdit中选择的文件将自动打开鼠标用UltraEdit编辑。","createtime":1555663308,"updatetime":1555663308,"commentNum":"1865","praiseNum":"98","shareNum":"786","authorId":"1212212","authorName":"逗你玩","categoryname":"新三板123","url":"","cover":"http://vod.zqrb.cn/157e21f555bd4f2b98941fd99626c70f/snapshots/312c9177bc9b454c88663cc1ef2716fb-00004.jpg"}]},{"id":123,"viewType":1,"title":"新闻列表","more":true,"icon":""},{"id":123,"viewType":2,"title":"标题✔图片X","summary":"文件可以从文件管理器中选择（多个文件可以选择），并拖入UltraEdit的窗口。如果鼠标按钮被释放超过UltraEdit中选择的文件将自动打开鼠标用UltraEdit编辑。","more":true,"dataType":0,"url":"","createtime":1555663308,"updatetime":1555663308,"commentNum":"1865","praiseNum":"98","shareNum":"786","authorId":"1212212","authorName":"逗你玩","source":"证券日报","categoryname":"新三板123"},{"id":123,"viewType":3,"title":"标题✔图片(小)✔","summary":"文件可以从文件管理器中选择（多个文件可以选择），并拖入UltraEdit的窗口。如果鼠标按钮被释放超过UltraEdit中选择的文件将自动打开鼠标用UltraEdit编辑。","more":true,"dataType":0,"url":"","categoryname":"新三板123","createtime":1555663308,"updatetime":1555663308,"commentNum":"1865","praiseNum":"98","shareNum":"786","authorId":"1212212","authorName":"逗你玩","cover":"http://vod.zqrb.cn/157e21f555bd4f2b98941fd99626c70f/snapshots/312c9177bc9b454c88663cc1ef2716fb-00004.jpg","coverBig":"http://vod.zqrb.cn/157e21f555bd4f2b98941fd99626c70f/snapshots/312c9177bc9b454c88663cc1ef2716fb-00004.jpg"},{"id":123,"viewType":4,"title":"标题✔图片(大)✔","summary":"文件可以从文件管理器中选择（多个文件可以选择），并拖入UltraEdit的窗口。如果鼠标按钮被释放超过UltraEdit中选择的文件将自动打开鼠标用UltraEdit编辑。","more":true,"dataType":0,"url":"","categoryname":"新三板123","createtime":1555663308,"updatetime":1555663308,"commentNum":"1865","praiseNum":"98","shareNum":"786","authorId":"1212212","authorName":"逗你玩","cover":"http://vod.zqrb.cn/15093469c30b4c40a7d4d4b70ac91d39/snapshots/4d2c826f559d4ea082465019c7ab81a0-00005.jpg","coverBig":"http://vod.zqrb.cn/15093469c30b4c40a7d4d4b70ac91d39/snapshots/4d2c826f559d4ea082465019c7ab81a0-00005.jpg"},{"id":123,"viewType":1,"title":"音频列表","more":true,"icon":""},{"id":123,"viewType":5,"title":"音频Item","showNum":3,"content":[{"id":123,"title":"绿卡能成功登陆新三板","mediaId":"440a4e0dd61e443696bf1542020790fb","mediaName":"","summary":"文件可以从文件管理器中选择（多个文件可以选择），并拖入UltraEdit的窗口。如果鼠标按钮被释放超过UltraEdit中选择的文件将自动打开鼠标用UltraEdit编辑。","dataType":2,"createtime":1555663308,"updatetime":1555663308,"commentNum":"1865","praiseNum":"98","shareNum":"786","authorId":"1212212","authorName":"逗你玩","url":"","categoryname":"新三板123","audio":"https://sharefs.yun.kugou.com/201912181334/6e9fc76badd7695a346c99a6eae746a8/G102/M09/06/1F/BocBAFjmRAWALvLzADxfadBBz6c483.mp3","cover":"http://vod.zqrb.cn/image/cover/39ECDE4DE91E49109F6270B569E1D8C5-6-2.png","coverBig":"http://vod.zqrb.cn/image/cover/39ECDE4DE91E49109F6270B569E1D8C5-6-2.png"},{"id":123,"title":"里华机械挂牌新三板","mediaId":"02c010748072406081ccf26c5cef4a61","mediaName":"","summary":"文件可以从文件管理器中选择（多个文件可以选择），并拖入UltraEdit的窗口。如果鼠标按钮被释放超过UltraEdit中选择的文件将自动打开鼠标用UltraEdit编辑。","dataType":2,"createtime":1555663308,"updatetime":1555663308,"commentNum":"1865","praiseNum":"98","shareNum":"786","authorId":"1212212","authorName":"逗你玩","categoryname":"新三板123","url":"","audio":"https://sharefs.yun.kugou.com/201912181334/6e9fc76badd7695a346c99a6eae746a8/G102/M09/06/1F/BocBAFjmRAWALvLzADxfadBBz6c483.mp3","cover":"http://vod.zqrb.cn/image/cover/816DCF7ECD184E849043C2DF22D00B76-6-2.png"},{"id":123,"title":"总经理助理金小山","mediaId":"157e21f555bd4f2b98941fd99626c70f","mediaName":"","dataType":2,"summary":"文件可以从文件管理器中选择（多个文件可以选择），并拖入UltraEdit的窗口。如果鼠标按钮被释放超过UltraEdit中选择的文件将自动打开鼠标用UltraEdit编辑。","createtime":1555663308,"updatetime":1555663308,"commentNum":"1865","praiseNum":"98","shareNum":"786","authorId":"1212212","authorName":"逗你玩","categoryname":"新三板123","url":"","audio":"https://sharefs.yun.kugou.com/201912181334/6e9fc76badd7695a346c99a6eae746a8/G102/M09/06/1F/BocBAFjmRAWALvLzADxfadBBz6c483.mp3","cover":"http://vod.zqrb.cn/image/default/0A303C1CAE2A4126995B20AAC6194A8F-6-2.JPG"}]}]
     * msg : 请求成功
     * status : 1
     */

    private String msg;
    private String status;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 123
         * viewType : 1
         * title : 热点聚焦
         * more : true
         * icon :
         * showNum : 1
         * content : [{"id":123,"title":"绿卡能成功登陆新三板","mediaId":"440a4e0dd61e443696bf1542020790fb","mediaName":"","summary":"文件可以从文件管理器中选择（多个文件可以选择），并拖入UltraEdit的窗口。如果鼠标按钮被释放超过UltraEdit中选择的文件将自动打开鼠标用UltraEdit编辑。","dataType":1,"createtime":1555663308,"updatetime":1555663308,"commentNum":"1865","praiseNum":"98","shareNum":"786","authorId":"1212212","authorName":"逗你玩","url":"","categoryname":"新三板123","audio":"","video":{"FD":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-24511cd56568c9dd72748c8fcf969373-fd.mp4","LD":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-901b338aaa1660de829114909d6d7ae0-ld.mp4","SD":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-562c47b777a979b643ac8aaf0b35f5dd-sd.mp4","HD":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-735fb9abede476e4feb1d74eddd2112f-hd.mp4","OD":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-c4c94e7f4918122b433281461812ea8d-od-S00000001-200000.mp4","K2":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-dc40c6992b38fb9fd8654c9bec79b3ed-2k.mp4"},"cover":"http://vod.zqrb.cn/image/cover/39ECDE4DE91E49109F6270B569E1D8C5-6-2.png","coverBig":"http://vod.zqrb.cn/image/cover/39ECDE4DE91E49109F6270B569E1D8C5-6-2.png"}]
         * summary : 文件可以从文件管理器中选择（多个文件可以选择），并拖入UltraEdit的窗口。如果鼠标按钮被释放超过UltraEdit中选择的文件将自动打开鼠标用UltraEdit编辑。
         * dataType : 0
         * url :
         * createtime : 1555663308
         * updatetime : 1555663308
         * commentNum : 1865
         * praiseNum : 98
         * shareNum : 786
         * authorId : 1212212
         * authorName : 逗你玩
         * source : 证券日报
         * categoryname : 新三板123
         * cover : http://vod.zqrb.cn/157e21f555bd4f2b98941fd99626c70f/snapshots/312c9177bc9b454c88663cc1ef2716fb-00004.jpg
         * coverBig : http://vod.zqrb.cn/157e21f555bd4f2b98941fd99626c70f/snapshots/312c9177bc9b454c88663cc1ef2716fb-00004.jpg
         */

        private int id;
        private int viewType;
        private String title;
        private boolean more;
        private String icon;
        private int showNum;
        private String summary;
        private int dataType;
        private String url;
        private int createtime;
        private int updatetime;
        private String commentNum;
        private String praiseNum;
        private String shareNum;
        private String authorId;
        private String authorName;
        private String source;
        private String categoryname;
        private String cover;
        private String coverBig;
        private List<ContentBean> content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getViewType() {
            return viewType;
        }

        public void setViewType(int viewType) {
            this.viewType = viewType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isMore() {
            return more;
        }

        public void setMore(boolean more) {
            this.more = more;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getShowNum() {
            return showNum == 0 ? 1 : showNum;
        }

        public void setShowNum(int showNum) {
            this.showNum = showNum;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public int getDataType() {
            return dataType;
        }

        public void setDataType(int dataType) {
            this.dataType = dataType;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getCreatetime() {
            return createtime;
        }

        public void setCreatetime(int createtime) {
            this.createtime = createtime;
        }

        public int getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(int updatetime) {
            this.updatetime = updatetime;
        }

        public String getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(String commentNum) {
            this.commentNum = commentNum;
        }

        public String getPraiseNum() {
            return praiseNum;
        }

        public void setPraiseNum(String praiseNum) {
            this.praiseNum = praiseNum;
        }

        public String getShareNum() {
            return shareNum;
        }

        public void setShareNum(String shareNum) {
            this.shareNum = shareNum;
        }

        public String getAuthorId() {
            return authorId;
        }

        public void setAuthorId(String authorId) {
            this.authorId = authorId;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getCategoryname() {
            return categoryname;
        }

        public void setCategoryname(String categoryname) {
            this.categoryname = categoryname;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getCoverBig() {
            return coverBig;
        }

        public void setCoverBig(String coverBig) {
            this.coverBig = coverBig;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class ContentBean {
            /**
             * id : 123
             * title : 绿卡能成功登陆新三板
             * mediaId : 440a4e0dd61e443696bf1542020790fb
             * mediaName :
             * summary : 文件可以从文件管理器中选择（多个文件可以选择），并拖入UltraEdit的窗口。如果鼠标按钮被释放超过UltraEdit中选择的文件将自动打开鼠标用UltraEdit编辑。
             * dataType : 1
             * createtime : 1555663308
             * updatetime : 1555663308
             * commentNum : 1865
             * praiseNum : 98
             * shareNum : 786
             * authorId : 1212212
             * authorName : 逗你玩
             * url :
             * categoryname : 新三板123
             * audio :
             * video : {"FD":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-24511cd56568c9dd72748c8fcf969373-fd.mp4","LD":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-901b338aaa1660de829114909d6d7ae0-ld.mp4","SD":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-562c47b777a979b643ac8aaf0b35f5dd-sd.mp4","HD":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-735fb9abede476e4feb1d74eddd2112f-hd.mp4","OD":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-c4c94e7f4918122b433281461812ea8d-od-S00000001-200000.mp4","K2":"http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-dc40c6992b38fb9fd8654c9bec79b3ed-2k.mp4"}
             * cover : http://vod.zqrb.cn/image/cover/39ECDE4DE91E49109F6270B569E1D8C5-6-2.png
             * coverBig : http://vod.zqrb.cn/image/cover/39ECDE4DE91E49109F6270B569E1D8C5-6-2.png
             */

            private int id;
            private String title;
            private String mediaId;
            private String mediaName;
            private String summary;
            private int dataType;
            private int createtime;
            private int updatetime;
            private String commentNum;
            private String praiseNum;
            private String shareNum;
            private String authorId;
            private String authorName;
            private String url;
            private String categoryname;
            private String audio;
            private VideoBean video;
            private String cover;
            private String coverBig;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getMediaId() {
                return mediaId;
            }

            public void setMediaId(String mediaId) {
                this.mediaId = mediaId;
            }

            public String getMediaName() {
                return mediaName;
            }

            public void setMediaName(String mediaName) {
                this.mediaName = mediaName;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public int getDataType() {
                return dataType;
            }

            public void setDataType(int dataType) {
                this.dataType = dataType;
            }

            public int getCreatetime() {
                return createtime;
            }

            public void setCreatetime(int createtime) {
                this.createtime = createtime;
            }

            public int getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(int updatetime) {
                this.updatetime = updatetime;
            }

            public String getCommentNum() {
                return commentNum;
            }

            public void setCommentNum(String commentNum) {
                this.commentNum = commentNum;
            }

            public String getPraiseNum() {
                return praiseNum;
            }

            public void setPraiseNum(String praiseNum) {
                this.praiseNum = praiseNum;
            }

            public String getShareNum() {
                return shareNum;
            }

            public void setShareNum(String shareNum) {
                this.shareNum = shareNum;
            }

            public String getAuthorId() {
                return authorId;
            }

            public void setAuthorId(String authorId) {
                this.authorId = authorId;
            }

            public String getAuthorName() {
                return authorName;
            }

            public void setAuthorName(String authorName) {
                this.authorName = authorName;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getCategoryname() {
                return categoryname;
            }

            public void setCategoryname(String categoryname) {
                this.categoryname = categoryname;
            }

            public String getAudio() {
                return audio;
            }

            public void setAudio(String audio) {
                this.audio = audio;
            }

            public VideoBean getVideo() {
                return video;
            }

            public void setVideo(VideoBean video) {
                this.video = video;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getCoverBig() {
                return coverBig;
            }

            public void setCoverBig(String coverBig) {
                this.coverBig = coverBig;
            }

            public static class VideoBean {
                /**
                 * FD : http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-24511cd56568c9dd72748c8fcf969373-fd.mp4
                 * LD : http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-901b338aaa1660de829114909d6d7ae0-ld.mp4
                 * SD : http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-562c47b777a979b643ac8aaf0b35f5dd-sd.mp4
                 * HD : http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-735fb9abede476e4feb1d74eddd2112f-hd.mp4
                 * OD : http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-c4c94e7f4918122b433281461812ea8d-od-S00000001-200000.mp4
                 * K2 : http://vod.zqrb.cn/440a4e0dd61e443696bf1542020790fb/cad880d101f240b49b885534a7a88380-dc40c6992b38fb9fd8654c9bec79b3ed-2k.mp4
                 */

                private String FD;
                private String LD;
                private String SD;
                private String HD;
                private String OD;
                private String K2;

                public String getFD() {
                    return FD;
                }

                public void setFD(String FD) {
                    this.FD = FD;
                }

                public String getLD() {
                    return LD;
                }

                public void setLD(String LD) {
                    this.LD = LD;
                }

                public String getSD() {
                    return SD;
                }

                public void setSD(String SD) {
                    this.SD = SD;
                }

                public String getHD() {
                    return HD;
                }

                public void setHD(String HD) {
                    this.HD = HD;
                }

                public String getOD() {
                    return OD;
                }

                public void setOD(String OD) {
                    this.OD = OD;
                }

                public String getK2() {
                    return K2;
                }

                public void setK2(String K2) {
                    this.K2 = K2;
                }
            }
        }
    }
}