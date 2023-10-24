package br.rocketseat.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

    public static void copyNonNullProperties(Object source, Object target){
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }
    
    public static String[] getNullPropertyNames(Object source){
        final BeanWrapper src = new BeanWrapperImpl(source);

        PropertyDescriptor[] pDescriptors = src.getPropertyDescriptors();

        Set<String> emptynames = new HashSet<>();

        for (PropertyDescriptor propertyDescriptor : pDescriptors) {
            Object srcValue = src.getPropertyValue(propertyDescriptor.getName());
            if (srcValue == null) {
                emptynames.add(propertyDescriptor.getName());
            }
        }

        String[] result = new String[emptynames.size()];

        return emptynames.toArray(result);
    }
}
