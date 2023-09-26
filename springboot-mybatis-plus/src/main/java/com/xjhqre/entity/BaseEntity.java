package com.xjhqre.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import org.springframework.util.ObjectUtils;

/**
 * <p>
 * Entity
 * <p>
 *
 * @author xjhqre
 * @since 9月 26, 2023
 */
public class BaseEntity {
    private static final long serialVersionUID = 1L;
    private final transient Set<String> propertyUpdated = new HashSet<>();

    public BaseEntity() {}

    public void onUpdateProperty(String field) {
        this.propertyUpdated.add(field);
    }

    public void onAfterPersistence() {
        this.propertyUpdated.clear();
    }

    public boolean isOnlyUpdateId(Supplier<String> idField) {
        return this.propertyUpdated.isEmpty() || !ObjectUtils.isEmpty(idField) && this.propertyUpdated.size() == 1
            && this.isPropertyUpdated(idField.get());
    }

    public boolean isPropertyUpdated(String field) {
        return this.propertyUpdated.contains(field);
    }
}
