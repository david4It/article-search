package com.scsme.zwy.articlesearch.util;

import org.apache.commons.beanutils.BeanUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @version V1.0
 * @ClassName: BeanJsonUtil
 * @Package common . IntelliJ IDEA
 * @Description:Map与Bean相互转换
 * @author: rmk
 * @date: 2018-04-02 11:04
 * @Copyright: 四川兴财信息产业发展有限公司. All rights reserved.
 */
public class BeanJsonUtil {

    /**
     * @Title:transMap2Bean2
     * @Description:Map转bean
     * @param:[map, obj]
     * @return:void
     * @author: rmk
     */
    public static void transMap2Bean2(Map<String, Object> map, Object obj) throws InvocationTargetException, IllegalAccessException {

        if (map == null || obj == null) {
            return;
        }
        try {
            BeanUtils.populate(obj, map);
        } catch (Exception e) {
            System.out.println("transMap2Bean2 Error " + e);
            throw e;
        }
    }

    /**
     * @Title:transBean2Map
     * @Description:bean转map
     * @param:[obj]
     * @return:java.util.Map<java.lang.String,java.lang.Object>
     * @author: rmk
     */
    public static Map<String, Object> transBean2Map(Object obj) {

        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }

        return map;

    }


}
