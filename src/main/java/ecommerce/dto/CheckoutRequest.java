package ecommerce.dto;

// 接收前端傳來的結帳資訊（例如收件人名稱、電話、地址）
public class CheckoutRequest {

	private String receiverName;
	private String receiverPhone;
	private String shippingLine1;
	private String shippingLine2;
	private String shippingCity;
	private String shippingPostalCode;
	
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	
	public String getShippingLine1() {
		return shippingLine1;
	}
	public void setShippingLine1(String shippingLine1) {
		this.shippingLine1 = shippingLine1;
	}
	
	public String getShippingLine2() {
		return shippingLine2;
	}
	public void setShippingLine2(String shippingLine2) {
		this.shippingLine2 = shippingLine2;
	}
	
	public String getShippingCity() {
		return shippingCity;
	}
	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}
	
	public String getShippingPostalCode() {
		return shippingPostalCode;
	}
	public void setShippingPostalCode(String shippingPostalCode) {
		this.shippingPostalCode = shippingPostalCode;
	}
	
	
}
