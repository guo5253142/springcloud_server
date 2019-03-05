package com.xxyp.admin.common.model.merchant;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xxyp.admin.common.model.BasePO;
import com.xxyp.admin.common.model.merchant.constant.MerchantInfoStatus;
/**
 * 商户信息model
 */
public class MerchantInfo extends BasePO {
     
	 private Long id;
	 
	 /** 商户名称 **/
	 private String merchantName;
	 /** 商户编号 **/
	 private String merchantNo;
	 /** 申请时间 **/
	 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	 private Date applyDate;
	 /** 法人姓名 **/
	 private String legalpersonName;
	 /** 法人证件类型 **/
	 private Integer legalpersonDoctype;
	 /** 商户类型 **/
	 private Integer type;
	 /** 法人证件号 **/
	 private String legalpersonDocno;
	 /** 法人手机号 **/
	 private String legalpersonPhone;
	 /** 商户状态 **/
	 private Integer status;
	 private String statusDesc;//状态 描述
	 
	 public String getStatusDesc(){
			if(null!=status){
				MerchantInfoStatus tempType=MerchantInfoStatus.getDescByIndex(MerchantInfoStatus.class, status);
				if(null!=tempType){
					return tempType.getDescription();
				}
			}
			return "";
	 }
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
	 
    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }
    public String getLegalpersonName() {
        return legalpersonName;
    }

    public void setLegalpersonName(String legalpersonName) {
        this.legalpersonName = legalpersonName;
    }
    public Integer getLegalpersonDoctype() {
        return legalpersonDoctype;
    }

    public void setLegalpersonDoctype(Integer legalpersonDoctype) {
        this.legalpersonDoctype = legalpersonDoctype;
    }
    public String getLegalpersonDocno() {
        return legalpersonDocno;
    }

    public void setLegalpersonDocno(String legalpersonDocno) {
        this.legalpersonDocno = legalpersonDocno;
    }
    public String getLegalpersonPhone() {
        return legalpersonPhone;
    }

    public void setLegalpersonPhone(String legalpersonPhone) {
        this.legalpersonPhone = legalpersonPhone;
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
