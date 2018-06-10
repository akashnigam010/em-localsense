package in.cw.sense.api.bo.setting.dto;

public class CloudConnectDto {
	private Integer restaurantId;
	private String password;
	private String privateKey;
	private String publicKey;
	private CloudDetailsDto cloudDetails;

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

	public CloudDetailsDto getCloudDetails() {
		return cloudDetails;
	}

	public void setCloudDetails(CloudDetailsDto cloudDetails) {
		this.cloudDetails = cloudDetails;
	}
}
