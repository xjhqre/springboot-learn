package com.xjhqre.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.SuppressAjWarnings;

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

    @SuppressAjWarnings({"adviceDidNotMatch"})
    @After(value = "execution(* set*(*)) && target(instance) && args(newValue)",
        argNames = "thisJoinPoint,instance,newValue")
    public void afterSetterMethod(JoinPoint thisJoinPoint, Object instance, Object newValue) {
        // char[] chars = thisJoinPointStaticPart.getSignature().getName().substring(3).toCharArray();
        // chars[0] = (char)(chars[0] + 32);
        // System.out.println(chars);
        // p.onUpdateProperty(String.valueOf(chars));
        System.out.println(thisJoinPoint.toString());

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
