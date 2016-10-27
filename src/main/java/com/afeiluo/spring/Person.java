package com.afeiluo.spring;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Person implements BeanFactoryAware, BeanNameAware, InitializingBean, DisposableBean, BeanFactoryPostProcessor,
        InstantiationAwareBeanPostProcessor, ApplicationContextAware {

    private String name;
    private String address;
    private int phone;

    private BeanFactory beanFactory;
    private String beanName;

    private ApplicationContext applicationContext;

    public Person() {
        System.out.println("【构造器】调用Person的构造器实例化");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("【注入属性】注入属性name");
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        System.out.println("【注入属性】注入属性address");
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        System.out.println("【注入属性】注入属性phone");
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Person [address=" + address + ", name=" + name + ", phone=" + phone + "]";
    }

    // 这是BeanFactoryAware接口方法
    @Override
    public void setBeanFactory(BeanFactory arg) throws BeansException {
        System.out.println("【BeanFactoryAware接口】调用BeanFactoryAware.setBeanFactory() ");
        this.beanFactory = arg;
    }

    // 这是BeanNameAware接口方法
    @Override
    public void setBeanName(String arg) {
        System.out.println("【BeanNameAware接口】调用BeanNameAware.setBeanName() name:" + arg);
        this.beanName = arg;
    }

    // 这是InitializingBean接口方法
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("【InitializingBean接口】调用InitializingBean.afterPropertiesSet()");
    }

    // 这是DiposibleBean接口方法
    @Override
    public void destroy() throws Exception {
        System.out.println("【DiposibleBean接口】调用DiposibleBean.destory()");
    }

    // 通过<bean>的init-method属性指定的初始化方法
    public void myInit() {
        System.out.println("【init-method】调用<bean>的init-method属性指定的初始化方法");
    }

    // 通过<bean>的destroy-method属性指定的初始化方法
    public void myDestory() {
        System.out.println("【destroy-method】调用<bean>的destroy-method属性指定的初始化方法");
    }

    // InstantiationAwareBeanPostProcessor
    @Override
    public boolean postProcessAfterInstantiation(Object arg0, String arg1) throws BeansException {
        System.out.println("【postProcessAfterInstantiation】");
        return false;
    }

    // InstantiationAwareBeanPostProcessor
    @Override
    public Object postProcessBeforeInstantiation(Class<?> arg0, String arg1) throws BeansException {
        System.out.println("【postProcessBeforeInstantiation】");
        return null;
    }

    // InstantiationAwareBeanPostProcessor
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues arg0, PropertyDescriptor[] arg1, Object arg2, String arg3) throws BeansException {
        System.out.println("【postProcessPropertyValues】");
        return null;
    }

    // BeanFactoryPostProcessor
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0) throws BeansException {
        System.out.println("【postProcessBeanFactory】");
    }

    // ApplicationContextAware
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("【setApplicationContext】");
        this.applicationContext = applicationContext;
    }

    // BeanPostProcessor
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("【BeanPostProcessor】接口方法postProcessAfterInitialization对属性进行更改！");
        return beanName;
    }

    // BeanPostProcessor
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("【BeanPostProcessor】接口方法postProcessBeforeInitialization对属性进行更改！");
        return beanName;
    }

}