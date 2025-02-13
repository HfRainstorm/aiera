package cn.hfstorm.aiera.system.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件信息
 *
 * @author hmy
 */
@Data
@NoArgsConstructor
public class SysFile implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 文件名称
	 */
	private String name;

	/**
	 * 文件地址
	 */
	private String url;

}
