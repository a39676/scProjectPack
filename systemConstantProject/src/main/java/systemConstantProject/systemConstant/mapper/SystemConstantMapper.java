package systemConstantProject.systemConstant.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import systemConstantProject.systemConstant.pojo.po.SystemConstant;
import systemConstantProject.systemConstant.pojo.po.SystemConstantExample;

public interface SystemConstantMapper {
    long countByExample(SystemConstantExample example);

    int deleteByExample(SystemConstantExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SystemConstant record);

    int insertSelective(SystemConstant record);

    List<SystemConstant> selectByExample(SystemConstantExample example);

    SystemConstant selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SystemConstant record, @Param("example") SystemConstantExample example);

    int updateByExample(@Param("record") SystemConstant record, @Param("example") SystemConstantExample example);

    int updateByPrimaryKeySelective(SystemConstant record);

    int updateByPrimaryKey(SystemConstant record);
    
    SystemConstant getValByName(String constantName);
    
    List<SystemConstant> getValsByName(List<String> constantNames);
}