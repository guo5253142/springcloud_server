package com.xxyp.admin.common.model.merchant.constant;

import com.xxyp.admin.common.common.constant.BaseType;

/**
 * 商户信息状态
 * 
 */
public class MerchantInfoStatus extends BaseType {
	
	protected MerchantInfoStatus(Integer index, String description) {
		super(index, description);
	}
	/**0, "禁用"**/
	public static MerchantInfoStatus forbidden = new MerchantInfoStatus(0, "禁用");
	/**1, "启用"**/
	public static MerchantInfoStatus  enabled = new MerchantInfoStatus(1, "启用");

}
