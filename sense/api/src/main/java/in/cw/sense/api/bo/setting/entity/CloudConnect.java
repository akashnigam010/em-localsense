package in.cw.sense.api.bo.setting.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "cloud_connect")
public class CloudConnect {
	@Id
	@Field("restaurantId")
	private Integer restaurantId;

	@Field("password")
	private String password;

	@Field("privateKey")
	private String privateKey;

	@Field("publicKey")
	private String publicKey;

	@Field("cloudDetails")
	private CloudDetails cloudDetails;

	public Integer getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public CloudDetails getCloudDetails() {
		return cloudDetails;
	}

	public void setCloudDetails(CloudDetails cloudDetails) {
		this.cloudDetails = cloudDetails;
	}
}
