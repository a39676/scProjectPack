package articleProject.article.mapper;

import articleProject.article.pojo.po.ArticleChannels;
import articleProject.article.pojo.po.ArticleChannelsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleChannelsMapper {
    long countByExample(ArticleChannelsExample example);

    int deleteByExample(ArticleChannelsExample example);

    int deleteByPrimaryKey(Integer channelId);

    int insert(ArticleChannels record);

    int insertSelective(ArticleChannels record);

    List<ArticleChannels> selectByExample(ArticleChannelsExample example);

    ArticleChannels selectByPrimaryKey(Integer channelId);

    int updateByExampleSelective(@Param("record") ArticleChannels record, @Param("example") ArticleChannelsExample example);

    int updateByExample(@Param("record") ArticleChannels record, @Param("example") ArticleChannelsExample example);

    int updateByPrimaryKeySelective(ArticleChannels record);

    int updateByPrimaryKey(ArticleChannels record);
}