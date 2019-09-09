package com.mvc.dresssup.chat;

import android.content.Context;
import android.text.TextUtils;

import com.hyphenate.helpdesk.model.AgentIdentityInfo;
import com.hyphenate.helpdesk.model.ContentFactory;
import com.hyphenate.helpdesk.model.QueueIdentityInfo;
import com.hyphenate.helpdesk.model.VisitorInfo;
import com.hyphenate.helpdesk.model.VisitorTrack;
import com.mvc.dresssup.base.Constants;
import com.mvc.dresssup.domain.ProductDetail;

/**
 * 对轨迹跟踪的消息操作 此类不是必须，只是为了演示和初始化一些数据
 */
public class DemoMessageHelper {

	public static final String IMAGE_URL_1 = "http://192.168.21.213/changerweb/upload/temporary/c28647ca4bd44be592de03d9dd72b85e.jpg";
	public static final String IMAGE_URL_2 = "http://o8ugkv090.bkt.clouddn.com/hd_two.png";
	public static final String IMAGE_URL_3 = "http://o8ugkv090.bkt.clouddn.com/hd_three.png";
	public static final String IMAGE_URL_4 = "http://o8ugkv090.bkt.clouddn.com/hd_four.png";


	public static VisitorInfo createVisitorInfo() {
		VisitorInfo info = ContentFactory.createVisitorInfo(null);
		info.nickName("xx")
		    .name("zddcccdsz")
		    .qq("10000")
			.phone("15811200000")
		    .companyName("easemob")
		    .description("")
		    .email("abc@123.com");
		return info;
	}




	public static VisitorTrack createVisitorTrack(Context context, ProductDetail productDetail) {
		VisitorTrack track = ContentFactory.createVisitorTrack(null);
			track.title(productDetail.getName())
                 .price("￥"+productDetail.getSalePrice())
                 .desc(productDetail.getDescription())
                 .imageUrl(Constants.Picture_URL1+productDetail.getPicture())
                 .itemUrl("http://www.baidu.com");
		return track;
	}

	
	public static AgentIdentityInfo createAgentIdentity(String agentName) {
		if (TextUtils.isEmpty(agentName)){
			return null;
		}
		AgentIdentityInfo info = ContentFactory.createAgentIdentityInfo(null);
		info.agentName(agentName);
		return info;
	}
	
	public static QueueIdentityInfo createQueueIdentity(String queueName) {
		if (TextUtils.isEmpty(queueName)){
			return null;
		}
		QueueIdentityInfo info = ContentFactory.createQueueIdentityInfo(null);
		info.queueName(queueName);
		return info;
	}

}
