package cn.hfstorm.aiera.system.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 角色
 *
 * @author hmy
 */

@Data
@NoArgsConstructor
public class RoleDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	private Long roleId;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 角色权限
	 */
	private String roleKey;

	/**
	 * 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限）
	 */
	private String dataScope;

}
