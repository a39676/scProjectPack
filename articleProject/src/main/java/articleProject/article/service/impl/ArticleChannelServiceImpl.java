package articleProject.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import articleProject.article.mapper.ArticleChannelsMapper;
import articleProject.article.service.ArticleChannelService;

@Service
public class ArticleChannelServiceImpl implements ArticleChannelService {

	@Autowired
	private ArticleChannelsMapper channelMapper;
	
	public void test() {
		channelMapper.selectByPrimaryKey(1);
	}
}
