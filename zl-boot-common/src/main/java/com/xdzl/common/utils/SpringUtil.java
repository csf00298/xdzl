package com.xdzl.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取当前spring容器
 * Created by yunjinchao on 2017/4/26.
 */
@Component
public class SpringUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext;
	@Override
	public void setApplicationContext(ApplicationContext arg) throws BeansException {
		applicationContext=arg;
	}

	/**
	 * 根据beanId获取实体对象
	 * @param beanId
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public  static<T> T getBean(String beanId, Class<T> clazz){
		T bean = applicationContext.getBean(beanId, clazz);
		return bean;
	}
	/**
	 * 根据类型获取实体对象
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public  static<T> T getBean(Class<T> clazz){
		T bean = applicationContext.getBean( clazz);
		return bean;
	}
}
