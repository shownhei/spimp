package cn.ccrise.spimp.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 页面属性注解
 * 用于生成前端jsp,js代码
 *
 * <p>
 *
 * @author David(david.kosoon@gmail.com)
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.FIELD })
public @interface PageFields {
	
	/**
	 * 属性描述
	 * @return
	 */
	public abstract String describtion();

	/**
	 * 是否允许为空
	 * @return
	 */
	public abstract boolean allowedNull() default true;
	
	/**
	 * 允许的最大长度
	 * @return
	 */
	public abstract int maxLenth() default 1024;
	
	/**
	 * 是否参与查询
	 * @return
	 */
	public abstract boolean search() default false;
	
	/**
	 * 文本类型
	 * @return
	 */
	public abstract String type() default "";
	
	/**
	 * 是否在表格中显示
	 * @return
	 */
	public abstract boolean columnShow() default true;
}
