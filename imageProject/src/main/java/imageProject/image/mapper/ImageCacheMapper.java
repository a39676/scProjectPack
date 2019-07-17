package imageProject.image.mapper;

import imageProject.image.domain.po.ImageCache;
import imageProject.image.domain.po.ImageCacheExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImageCacheMapper {
    long countByExample(ImageCacheExample example);

    int deleteByExample(ImageCacheExample example);

    int deleteByPrimaryKey(Integer imageId);

    int insert(ImageCache record);

    int insertSelective(ImageCache record);

    List<ImageCache> selectByExample(ImageCacheExample example);

    ImageCache selectByPrimaryKey(Integer imageId);

    int updateByExampleSelective(@Param("record") ImageCache record, @Param("example") ImageCacheExample example);

    int updateByExample(@Param("record") ImageCache record, @Param("example") ImageCacheExample example);

    int updateByPrimaryKeySelective(ImageCache record);

    int updateByPrimaryKey(ImageCache record);
}