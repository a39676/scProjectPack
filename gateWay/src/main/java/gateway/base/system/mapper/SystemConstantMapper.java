package gateway.base.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import gateway.base.system.pojo.bo.SystemConstant;

@Mapper
public interface SystemConstantMapper {
    int insert(SystemConstant record);

    int insertSelective(SystemConstant record);
    
    SystemConstant getValByName(String constantName);
    
    List<SystemConstant> getValsByName(List<String> constantNames);
}