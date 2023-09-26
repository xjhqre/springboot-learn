package com.xjhqre.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.SuppressAjWarnings;

import com.xjhqre.entity.BaseEntity;

/**
 * <p>
 * EntityAspect
 * <p>
 *
 * @author xjhqre
 * @since 9月 26, 2023
 */
@Aspect
// @Component
public class EntityAspect {
    public EntityAspect() {}

    // 定义切点
    @Pointcut(value = "execution(* set*(*)) && target(BaseEntity) && args(newValue)", argNames = "BaseEntity,newValue")
    public void setterMethod(BaseEntity BaseEntity, Object newValue) {}

    @SuppressAjWarnings({"adviceDidNotMatch"})
    @After(value = "setterMethod(BaseEntity, newValue)", argNames = "thisJoinPoint,BaseEntity,newValue")
    public void afterSetterMethod(JoinPoint thisJoinPoint, BaseEntity BaseEntity, Object newValue) {
        // char[] chars = thisJoinPoint.getSignature().getName().substring(3).toCharArray();
        // chars[0] = (char)(chars[0] + 32);
        // BaseEntity.onUpdateProperty(String.valueOf(chars));
        // System.out.println(thisJoinPoint.toString());
        System.out.println("切面");

    }

    // @SuppressAjWarnings({"adviceDidNotMatch"})
    // @After(value = "updateResetById(e)", argNames = "e")
    // public void ajc$after$com_ccssoft_ngbip_aspectj_entity_EntityAspect$2$b57c3894(BaseEntity e) {
    // e.onAfterPersistence();
    // }

    // public static EntityAspect aspectOf() {
    // if (ajc$perSingletonInstance == null) {
    // throw new NoAspectBoundException("com_ccssoft_ngbip_aspectj_entity_EntityAspect", ajc$initFailureCause);
    // } else {
    // return ajc$perSingletonInstance;
    // }
    // }
    //
    // public static boolean hasAspect() {
    // return ajc$perSingletonInstance != null;
    // }
}
