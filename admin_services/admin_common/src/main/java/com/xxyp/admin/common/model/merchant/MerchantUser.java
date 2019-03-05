package com.xxyp.admin.common.model.merchant;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xxyp.admin.common.model.BasePO;
import com.xxyp.admin.common.model.merchant.constant.MerchantUserStatus;
/**
 * 商户用户model
 */
public class MerchantUser extends BasePO {
     
	 private Long id;
	 
	 /** 商户id **/
	 private Long merchantId;
	 /** 商户编号 **/
	 private String merchantNo;
	 /** 商户名称 **/
	 private String merchantName;
	 /** 商户信息 **/
	 private MerchantInfo merchantInfo;
	 /** 用户名 **/
	 private String account;
	 /** email **/
	 private String email;
	 /** 密码 **/
	 private String password;
	 /** 商户角色id **/
	 private Long merchantRoleId;
	 /** 商户角色名称 **/
	 private String merchantRoleName;
	 /** 姓名 **/
	 private String name;
	 /** 联系电话 **/
	 private String phone;
	 /** 备注 **/
	 private String remark;
	 /** 状态 **/
	 private Integer status;
	 
	 private String statusDesc;//状态 描述
	 
	 public String getStatusDesc(){
			if(null!=status){
				MerchantUserStatus tempType=MerchantUserStatus.getDescByIndex(MerchantUserStatus.class, status);
				if(null!=tempType){
					return tempType.getDescription();
				}
			}
			return "";
	 }
	 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	 private Date lastLoginTime;//上一次登录时间
	 /**  **/
	 private Integer usedTag;
	 /**  **/
	 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	 private Date createDate;
	 /**  **/
	 private Long creator;
	 /**  **/
	 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	 private Date editDate;
	 /**  **/
	 private Long editor;
	 
	 
    public MerchantInfo getMerchantInfo() {
		return merchantInfo;
	}

	public void setMerchantInfo(MerchantInfo merchantInfo) {
		this.merchantInfo = merchantInfo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getMerchantRoleName() {
		return merchantRoleName;
	}

	public void setMerchantRoleName(String merchantRoleName) {
		this.merchantRoleName = merchantRoleName;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Long getMerchantRoleId() {
        return merchantRoleId;
    }

    public void setMerchantRoleId(Long merchantRoleId) {
        this.merchantRoleId = merchantRoleId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getUsedTag() {
        return usedTag;
    }

    public void setUsedTag(Integer usedTag) {
        this.usedTag = usedTag;
    }
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }
    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }
    public Long getEditor() {
        return editor;
    }

    public void setEditor(Long editor) {
        this.editor = editor;
    }

}
