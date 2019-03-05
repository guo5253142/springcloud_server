package com.xxyp.admin.common.model.merchant.constant;

import com.xxyp.admin.common.common.constant.BaseType;

/**
 * 商户用户状态
 * 
 */
public class MerchantUserStatus extends BaseType {
	
	protected MerchantUserStatus(Integer index, String description) {
		super(index, description);
	}
	/**0, "禁用"**/
	public static MerchantUserStatus forbidden = new MerchantUserStatus(0, "禁用");
	/**1, "启用"**/
	public static MerchantUserStatus  enabled = new MerchantUserStatus(1, "启用");

}
