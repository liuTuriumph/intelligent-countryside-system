package com.kivze.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

@MappedTypes(List.class)
@MappedJdbcTypes(value = JdbcType.VARCHAR,includeNullJdbcType = true)
public class MyDateTypeHandler extends BaseTypeHandler<List<String>> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<String> strings, JdbcType jdbcType) throws SQLException {
        //遍历List装换为String类型
        StringBuffer sb  = new StringBuffer();
        for (int j=0;j< strings.size();j++){
            if (j == strings.size() -1){
                sb.append(strings.get(j));
            }else {
                sb.append(strings.get(j)).append(",");
            }
        }
        preparedStatement.setString(i,sb.toString());
    }

    @Override
    public List<String> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        //获取String类型的结果，使用“，”分割为List后返回
        String resultString = resultSet.getString(s);
        if (StringUtils.isNoneEmpty(resultString)){
            return Arrays.asList(resultString.split(","));
        }
        return null;
    }

    @Override
    public List<String> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        // 获取String类型的结果，使用","分割为List后返回
        String resultString = resultSet.getString(i);
        if (StringUtils.isNotEmpty(resultString)) {
            return Arrays.asList(resultString.split(","));
        }
        return null;
    }

    @Override
    public List<String> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        // 获取String类型的结果，使用","分割为List后返回
        String resultString = callableStatement.getString(i);
        if (StringUtils.isNotEmpty(resultString)) {
            return Arrays.asList(resultString.split(","));
        }
        return null;
    }
}
