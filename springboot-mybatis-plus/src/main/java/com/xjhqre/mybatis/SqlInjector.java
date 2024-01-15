
package com.xjhqre.mybatis;

import static java.util.stream.Collectors.toList;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;
import com.baomidou.mybatisplus.core.injector.methods.Delete;
import com.baomidou.mybatisplus.core.injector.methods.DeleteBatchByIds;
import com.baomidou.mybatisplus.core.injector.methods.DeleteById;
import com.baomidou.mybatisplus.core.injector.methods.DeleteByMap;
import com.baomidou.mybatisplus.core.injector.methods.Insert;
import com.baomidou.mybatisplus.core.injector.methods.SelectBatchByIds;
import com.baomidou.mybatisplus.core.injector.methods.SelectById;
import com.baomidou.mybatisplus.core.injector.methods.SelectByMap;
import com.baomidou.mybatisplus.core.injector.methods.SelectCount;
import com.baomidou.mybatisplus.core.injector.methods.SelectList;
import com.baomidou.mybatisplus.core.injector.methods.SelectMaps;
import com.baomidou.mybatisplus.core.injector.methods.SelectMapsPage;
import com.baomidou.mybatisplus.core.injector.methods.SelectObjs;
import com.baomidou.mybatisplus.core.injector.methods.SelectOne;
import com.baomidou.mybatisplus.core.injector.methods.SelectPage;
import com.baomidou.mybatisplus.core.injector.methods.Update;
import com.baomidou.mybatisplus.core.injector.methods.UpdateById;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;

public class SqlInjector extends AbstractSqlInjector {
    private static final Log logger = LogFactory.getLog(SqlInjector.class);

    public SqlInjector() {}

    public void inspectInject(MapperBuilderAssistant builderAssistant, Class<?> mapperClass) {
        Class<?> modelClass = getMapperModelClass(mapperClass); // 获取mapper实现接口的第一个泛型参数类型
        if (modelClass != null) {
            String className = mapperClass.toString();
            Set<String> mapperRegistryCache =
                GlobalConfigUtils.getMapperRegistryCache(builderAssistant.getConfiguration());
            if (!mapperRegistryCache.contains(className)) {
                List<AbstractMethod> methodList = this.getMethodList(mapperClass);
                if (!CollectionUtils.isEmpty(methodList)) {
                    TableInfo tableInfo = TableInfoHelper.initTableInfo(builderAssistant, modelClass);
                    this.reSetTableName(mapperClass, tableInfo); // 多了这个方法
                    // logger.info("reSetTableNameasdasdasdasdasdasd");
                    methodList.forEach((m) -> {
                        m.inject(builderAssistant, mapperClass, modelClass, tableInfo);
                    }); // 注入自定义方法
                } else {
                    logger.debug(mapperClass.toString() + ", No effective injection method was found.");
                }

                mapperRegistryCache.add(className);
            }
        }

    }

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        return Stream.of(new Insert(), new Delete(), new DeleteByMap(), new DeleteById(), new DeleteBatchByIds(),
            new Update(), new UpdateById(), new SelectById(), new SelectBatchByIds(), new SelectByMap(),
            new SelectOne(), new SelectCount(), new SelectMaps(), new SelectMapsPage(), new SelectObjs(),
            new SelectList(), new SelectPage()).collect(toList());
    }

    /**
     * 提取泛型模型,多泛型的时候请将泛型T放在第一位
     *
     * @param mapperClass
     *            mapper 接口
     * @return mapper 泛型
     */
    public static Class<?> getMapperModelClass(Class<?> mapperClass) {
        Type[] types = mapperClass.getGenericInterfaces(); // 获取mapper实现的所有接口
        ParameterizedType target = null;
        Type[] copyTypes = types;
        int typesLength = types.length;

        for (int i = 0; i < typesLength; ++i) {
            Type type = copyTypes[i];
            if (type instanceof ParameterizedType) { // 判断是否为参数化类型（即带有泛型参数的类型）
                Type[] typeArray = ((ParameterizedType)type).getActualTypeArguments(); // 获取所有的泛型参数
                if (ArrayUtils.isNotEmpty(typeArray) && typeArray.length != 0) {
                    Type t = typeArray[0];
                    if (!(t instanceof TypeVariable) && !(t instanceof WildcardType)) {
                        target = (ParameterizedType)type;
                    }
                }
                break;
            }

            if (type instanceof Class) {
                return getMapperModelClass((Class)type);
            }
        }

        return target == null ? null : (Class)target.getActualTypeArguments()[0];
    }

    public static void reSetTableName(Class<?> mapperClass, TableInfo tableInfo) {
        TableName table = (TableName)mapperClass.getAnnotation(TableName.class); // 获取mapper上的TableName注解
        // 如果mapper上标注了TableName注解
        if (table != null && StringUtils.isNotEmpty(table.value())) {
            String targetTableName = table.value(); // 获取注解里写的表名

            if (StringUtils.isNotEmpty(table.schema())) { // 注解里是否写了模式
                targetTableName = table.schema() + "." + targetTableName;
            }

            Field declaredField = ReflectionUtils.findField(TableInfo.class, "tableName");
            declaredField.setAccessible(true);

            try {
                declaredField.set(tableInfo, targetTableName);
            } catch (IllegalAccessException | IllegalArgumentException exception) {
                exception.printStackTrace();
            }
        }

    }
}