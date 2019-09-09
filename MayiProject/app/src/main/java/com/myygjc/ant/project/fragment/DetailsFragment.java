package com.myygjc.ant.project.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.myygjc.ant.project.R;
import com.myygjc.ant.project.util.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/3/29.
 */

public class DetailsFragment extends Fragment {
    @Bind(R.id.wv)
    WebView wv;
    private WebSettings webSettings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_detail, null);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        String mstp = bundle.getString("mstp");

        if (mstp.equals("没有")) {
            wv.setVisibility(View.GONE);
        } else {
            String[] split = mstp.split(",");
            StringBuilder sb = new StringBuilder();
            //拼接一段HTML代码
            sb.append("<html>");
            sb.append("<head>");
            sb.append("<title>商品详情</title>");
            sb.append("</head>");
            sb.append("<body>");
            for (int i = 0; i < split.length; i++) {
                sb.append("<div align=center><img src='http://123.206.107.160/" + split[i] + "' width=100%/></div>");
            }
            sb.append("</body>");
            sb.append("</html>");
            wv.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null);
//            webSettings = wv.getSettings();
//            webSettings.setLoadWithOverviewMode(true);
//            webSettings.setBuiltInZoomControls(true);
//            webSettings.setDisplayZoomControls(false);
//            webSettings.setLoadsImagesAutomatically(true);
//            webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//            webSettings.setBlockNetworkImage(true);
//            webSettings.setUseWideViewPort(true);
//            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//            wv.setWebViewClient(new GoodsDetailWebViewClient());

        }
        return view;
    }


//    private class GoodsDetailWebViewClient extends WebViewClient {
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//            webSettings.setBlockNetworkImage(false);
//        }
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            return true;
//        }
//    }

}
