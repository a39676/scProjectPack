package articleProject.article.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import articleProject.article.pojo.po.ArticleLong;
import articleProject.article.pojo.po.ArticleLongExample;

public interface ArticleLongMapper {
    long countByExample(ArticleLongExample example);

    int deleteByExample(ArticleLongExample example);

    int deleteByPrimaryKey(Long articleId);

    int insert(ArticleLong record);

    int insertSelective(ArticleLong record);

    List<ArticleLong> selectByExample(ArticleLongExample example);

    ArticleLong selectByPrimaryKey(Long articleId);

    int updateByExampleSelective(@Param("record") ArticleLong record, @Param("example") ArticleLongExample example);

    int updateByExample(@Param("record") ArticleLong record, @Param("example") ArticleLongExample example);

    int updateByPrimaryKeySelective(ArticleLong record);

    int updateByPrimaryKey(ArticleLong record);
}