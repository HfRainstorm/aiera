package cn.hfstorm.aiera.common.core.annotation;

import cn.hfstorm.aiera.common.core.constant.DbTypeConst;
import com.baomidou.dynamic.datasource.annotation.DS;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : hmy
 * @date : 2025/2/12 17:28
 */
@DS(DbTypeConst.BIZ_DB)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DbChoice {
}
