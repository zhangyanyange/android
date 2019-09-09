package com.myygjc.ant.project.doman;

/**
 * @author Administrator
 * ֧����ʱ���������ĵ�����
 * ������������Լ���װ��.��΢��֧����sdkû�й�ϵ
 */



/**
{"sign_method":"SHA1",
"timestamp":"1448183134",
"noncestr":"2015112214111705343237",
"partnerid":"1218554401",
"app_signature":"63c28085254a1e629a462e54b0a6d7d7b36d68ca",
"prepayid":"82010380001511220a596b1eee0eadb8",
"package":"Sign=WXPay",
"appid":"wx71d0b68f17483ced"}
 */
public class WXPayData {
	private String sign_method;
	private String timestamp;
	private String noncestr;
	private String partnerid;
	private String app_signature;
	private String prepayid;
	private String package1;
	private String appid;

	public String getSign_method() {
		return sign_method;
	}

	public void setSign_method(String sign_method) {
		this.sign_method = sign_method;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}

	public String getApp_signature() {
		return app_signature;
	}

	public void setApp_signature(String app_signature) {
		this.app_signature = app_signature;
	}

	public String getPrepayid() {
		return prepayid;
	}

	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}

	public String getPackage1() {
		return package1;
	}

	public void setPackage1(String package1) {
		this.package1 = package1;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public WXPayData(String sign_method, String timestamp, String noncestr, String partnerid, String app_signature,
			String prepayid, String package1, String appid) {
		super();
		this.sign_method = sign_method;
		this.timestamp = timestamp;
		this.noncestr = noncestr;
		this.partnerid = partnerid;
		this.app_signature = app_signature;
		this.prepayid = prepayid;
		this.package1 = package1;
		this.appid = appid;
	}

	@Override
	public String toString() {
		return "WXPayData [sign_method=" + sign_method + ", timestamp=" + timestamp + ", noncestr=" + noncestr
				+ ", partnerid=" + partnerid + ", app_signature=" + app_signature + ", prepayid=" + prepayid
				+ ", package1=" + package1 + ", appid=" + appid + "]";
	}

}
