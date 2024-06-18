package com.programmingtechie.order_service.utils;

import java.lang.reflect.Field;

public class MapperUtil {
    public static<T,U> T mapToDto(U source, Class<T> targetClass){
       try{
          T target = targetClass.getDeclaredConstructor().newInstance();
           Field[] sourceFields = source.getClass().getDeclaredFields();
           Field[] targetedFields = targetClass.getDeclaredFields();

           for(Field sourceField : sourceFields){
               sourceField.setAccessible(true);
               Object value = sourceField.get(source);
               for(Field targetField : targetedFields){
                   if(sourceField.getName().equals(targetField.getName()) && sourceField.getType().equals(targetField.getType())){
                       targetField.setAccessible(true);
                       targetField.set(target, value);
                       break;
                   }
               }
           }

           return target;
       } catch (Exception e){
          throw new RuntimeException(e);
       }
    }
}
