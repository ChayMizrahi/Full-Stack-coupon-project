package com.chay.couponprojectspring.entities;

/**
 * The object will be injected into the session after a valid login.The object
 * contains information about the type of user who logged in (admin / customer /
 * company) and the user's ID and allows for authentication of the user before
 * any action to perform.
 * 
 * @author Chay Mizrahi
 *
 */
public class CustomLogin {

	private LoginType loginType;
	private int typeId;

	public CustomLogin(LoginType loginType, int typeId) {
		this.loginType = loginType;
		this.typeId = typeId;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	@Override
	public String toString() {
		return "CustomLogin [loginType=" + loginType + ", typeId=" + typeId + "]";
	}

}
