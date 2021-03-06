package com.lbt.icon.demanddraft.config;


import com.lbt.icon.sec.security.domain.role.rolepermissions.permission.PermissionAccessType;
import com.lbt.icon.sec.security.domain.role.rolepermissions.permission.PermissionSource;
import com.lbt.icon.sec.security.domain.role.rolepermissions.permission.ResourceType;
import lombok.*;

import java.util.List;

@Getter
public enum DDProductPermissionEnum implements PermissionSource {
	
	CREATE_DD_PRODUCT("Create", "Create demand draft product", Authority.CREATE_DD_PRODUCT, PermissionAccessType.WRITE, PermissionConstants.DD_PRODUCT_RULE_NAME, ResourceType.CORE),
	UPDATE_DD_PRODUCT("Update", "Update demand draft product", Authority.UPDATE_DD_PRODUCT, PermissionAccessType.WRITE, PermissionConstants.DD_PRODUCT_RULE_NAME, ResourceType.CORE),
	ENABLE_DD_PRODUCT("Enable", "Enable demand draft product", Authority.ENABLE_DD_PRODUCT, PermissionAccessType.WRITE, PermissionConstants.DD_PRODUCT_RULE_NAME, ResourceType.CORE),
	DISABLE_DD_PRODUCT("Disable", "Disable demand draft product", Authority.DISABLE_DD_PRODUCT, PermissionAccessType.WRITE, PermissionConstants.DD_PRODUCT_RULE_NAME, ResourceType.CORE),
	AUTHORIZE_DD_PRODUCT("Authorize", "Authorize demand draft product", Authority.AUTHORIZE_DD_PRODUCT, PermissionAccessType.WRITE, PermissionConstants.DD_PRODUCT_RULE_NAME, ResourceType.CORE);

	private final String name;
	private final String description;
	private final String systemName;
	private final PermissionAccessType permissionAccessType;
	private final String subCategory;
	private final String category = "DEMAND DRAFT PRODUCT";
	private final ResourceType resourceType;

	DDProductPermissionEnum(String name, String description, String systemName, PermissionAccessType permissionAccessType, String subCategory, ResourceType resourceType) {
		this.name = name;
		this.description = description;
		this.systemName = systemName;
		this.permissionAccessType = permissionAccessType;
		this.subCategory = subCategory;
		this.resourceType = resourceType;
	}

	@Override
	public List<String> getDependentPermissions() {
		return null;
	}

	@NoArgsConstructor(access=AccessLevel.PRIVATE)
	public class Authority {
		public static final String CREATE_DD_PRODUCT = "CREATE_DD_PRODUCT";
		public static final String UPDATE_DD_PRODUCT = "UPDATE_DD_PRODUCT";
		public static final String ENABLE_DD_PRODUCT = "ENABLE_DD_PRODUCT";
		public static final String DISABLE_DD_PRODUCT = "DISABLE_DD_PRODUCT";
		public static final String AUTHORIZE_DD_PRODUCT = "AUTHORIZE_DD_PRODUCT";
	}
	
	@NoArgsConstructor(access=AccessLevel.PRIVATE)
	public class PermissionConstants {
		public static final String DD_PRODUCT_RULE_NAME = "Demand draft rule";
	}


}
