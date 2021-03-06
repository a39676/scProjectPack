package gateway.base.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import gateway.base.user.pojo.dto.FindAuthsConditionDTO;
import gateway.base.user.pojo.po.Auth;
import gateway.base.user.pojo.po.AuthExample;

@Mapper
public interface AuthMapper {
    long countByExample(AuthExample example);

    int deleteByExample(AuthExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Auth record);

    int insertSelective(Auth record);

    List<Auth> selectByExample(AuthExample example);

    Auth selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

    List<Auth> findAuthsByUserId(Long userId);
    
    List<Long> findUserIdByAuthId(Long authId);
    
    List<Auth> findAuthsByCondition(FindAuthsConditionDTO dto);
    
}