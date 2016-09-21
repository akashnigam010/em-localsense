package in.cw.sense.api.bo.setting.entity;

import org.springframework.data.mongodb.core.mapping.Field;

public class CloudDetails {
	@Field("cloudUrl")
	private String cloudUrl;

	@Field("publicKey")
	private String publicKey;

	public String getCloudUrl() {
		return cloudUrl;
	}

	public void setCloudUrl(String cloudUrl) {
		this.cloudUrl = cloudUrl;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
}
