package scAppDemo.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import scAppDemo.base.domain.po.Roles;
import scAppDemo.base.domain.po.RolesExample;

@Mapper
public interface RolesMapper {
    long countByExample(RolesExample example);

    int deleteByExample(RolesExample example);

    int deleteByPrimaryKey(Long roleId);

    int insert(Roles record);

    int insertSelective(Roles record);

    List<Roles> selectByExample(RolesExample example);

    Roles selectByPrimaryKey(Long roleId);

    int updateByExampleSelective(@Param("record") Roles record, @Param("example") RolesExample example);

    int updateByExample(@Param("record") Roles record, @Param("example") RolesExample example);

    int updateByPrimaryKeySelective(Roles record);

    int updateByPrimaryKey(Roles record);
}