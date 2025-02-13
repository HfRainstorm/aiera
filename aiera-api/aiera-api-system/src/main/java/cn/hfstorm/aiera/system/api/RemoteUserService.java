package cn.hfstorm.aiera.system.api;


import cn.hfstorm.aiera.common.core.exception.ServiceException;
import cn.hfstorm.aiera.common.core.exception.user.UserException;
import cn.hfstorm.aiera.system.api.domain.bo.RemoteUserBo;
import cn.hfstorm.aiera.system.api.model.LoginUser;
import cn.hfstorm.aiera.system.api.model.XcxLoginUser;

/**
 * 用户服务
 *
 * @author hmy
 */
public interface RemoteUserService {

	/**
	 * 通过用户名查询用户信息
	 *
	 * @param username 用户名
	 * @return 结果
	 */
	LoginUser getUserInfo(String username) throws UserException;

	/**
	 * 通过用户id查询用户信息
	 *
	 * @param userId   用户id
	 * @return 结果
	 */
	LoginUser getUserInfo(Long userId) throws UserException;

	/**
	 * 通过手机号查询用户信息
	 *
	 * @param phonenumber 手机号
	 * @return 结果
	 */
	LoginUser getUserInfoByPhonenumber(String phonenumber) throws UserException;

	/**
	 * 通过邮箱查询用户信息
	 *
	 * @param email    邮箱
	 * @return 结果
	 */
	LoginUser getUserInfoByEmail(String email) throws UserException;

	/**
	 * 通过openid查询用户信息
	 *
	 * @param openid openid
	 * @return 结果
	 */
	XcxLoginUser getUserInfoByOpenid(String openid) throws UserException;

	/**
	 * 注册用户信息
	 *
	 * @param remoteUserBo 用户信息
	 * @return 结果
	 */
	Boolean registerUserInfo(RemoteUserBo remoteUserBo) throws UserException, ServiceException;

	/**
	 * 通过userId查询用户账户
	 *
	 * @param userId 用户id
	 * @return 结果
	 */
	String selectUserNameById(Long userId);

	/**
	 * 通过用户ID查询用户昵称
	 *
	 * @param userId 用户id
	 * @return 结果
	 */
	String selectNicknameById(Long userId);

	/**
	 * 更新用户信息
	 *
	 * @param userId 用户ID
	 * @param ip     IP地址
	 */
	void recordLoginInfo(Long userId, String ip);

}
