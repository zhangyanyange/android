package project.mvp.com.myapplication;

/**
 * 创建人 张岩
 * 时间   2019/2/20.
 * 类描述
 */
public class CallBack {
        public OnAddCartListener onAddCartListener;
        public interface OnAddCartListener{
            void addCart(String position);
        }

        public void setOnAddCartListener(OnAddCartListener onAddCartListener) {
            this.onAddCartListener = onAddCartListener;
        }

}
