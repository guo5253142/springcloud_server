package com.xxyp.admin.common.model.system;

import java.util.List;

import com.xxyp.admin.common.common.constant.YesOrNoType;
import com.xxyp.admin.common.model.BasePO;

 public class Permission extends BasePO {



	private static final long serialVersionUID = 4767307740910429540L;

    private Long id;

    private String name;//权限名
    
    private String loadDataMethodName;//加载数据方法名

    private String menuUrl;//菜单地址
    private List<PermissionItem> permissionItemList;
    private Module module;
    
    private String moduleName;//模块名称
    
    private Long moduleId; //模块ID
    
    private Integer orderIndex;
    
  /*  @JsonIgnore*/
    private Integer isMenu;//是否是菜单
    
    private String isMenuDesc;
    
    // 用于修改权限时候， 非数据库字段
    private boolean isChecked;
    
    
    public String getIsMenuDesc() {
    	if(null!=isMenu){
			YesOrNoType tempType=YesOrNoType.getDescByIndex(YesOrNoType.class, isMenu);
			if(null!=tempType){
				return tempType.getDescription();
			}
		}
		return "";
	}
	public Integer getIsMenu() {
		return isMenu;
	}
	public void setIsMenu(Integer isMenu) {
		this.isMenu = isMenu;
	}
	public String getLoadDataMethodName() {
		return loadDataMethodName;
	}
	public void setLoadDataMethodName(String loadDataMethodName) {
		this.loadDataMethodName = loadDataMethodName;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public Permission(){}
    public Permission(Long id) {
		this.id = id;
	}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public List<PermissionItem> getPermissionItemList() {
        return permissionItemList;
    }

    public void setPermissionItemList(List<PermissionItem> permissionItemList) {
        this.permissionItemList = permissionItemList;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	public Integer getOrderIndex() {
		return orderIndex;
	}
	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}
	

}
