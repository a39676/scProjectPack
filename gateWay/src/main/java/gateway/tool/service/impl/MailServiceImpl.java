package gateway.tool.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.SearchTerm;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import dateHandle.DateUtilCustom;
import emailHandle.MailHandle;
import emailHandle.mailService.send.SendEmail;
import gateway.base.system.service.SchedualServiceSystemConstant;
import gateway.base.user.pojo.bo.UserMailAndMailKeyBO;
import gateway.base.user.service.UsersService;
import gateway.common.constant.ResourceConstant;
import gateway.common.service.CommonService;
import gateway.tool.mapper.MailRecordMapper;
import gateway.tool.pojo.constant.ToolPathConstant;
import gateway.tool.pojo.param.InsertNewMailRecordParam;
import gateway.tool.pojo.po.MailRecord;
import gateway.tool.pojo.type.MailType;
import gateway.tool.service.MailService;
import ioHandle.FileUtilCustom;
import scAppCommon.constant.url.UsersUrlConstant;
import scAppCommon.pojo.result.CommonResult;
import scAppCommon.pojo.type.ResultType;
import systemConstant.pojo.bo.SystemConstantStore;
import systemConstant.pojo.dto.GetValsByNameDto;

@Service
public class MailServiceImpl extends CommonService implements MailService {

	@Autowired
	private SchedualServiceSystemConstant systemConstantService;
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private MailRecordMapper mailRecordMapper;
	
	
	
	private boolean isMailReady() {
		if(redisTemplate.hasKey(SystemConstantStore.adminMailName) && redisTemplate.hasKey(SystemConstantStore.adminMailPwd)) {
			return true;
		} else {
			GetValsByNameDto dto = new GetValsByNameDto();
			dto.setConstantNames(Arrays.asList(SystemConstantStore.adminMailName, SystemConstantStore.adminMailPwd));
			systemConstantService.getValsByName(dto);
			if(redisTemplate.hasKey(SystemConstantStore.adminMailName) && redisTemplate.hasKey(SystemConstantStore.adminMailPwd)) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	
	public CommonResult sendSimpleMail(Long userId, String sendTo, String title, String content, String mailKey, MailType mailType) {
		CommonResult result = new CommonResult();
		if(userId == null || mailType == null || mailType.getCode() == null) {
			result.failWithMessage(ResultType.nullParam.getName());
			return result;
		}
		if(!isMailReady()) {
			result.failWithMessage(ResultType.mailBaseOptionError.getName());
			return result;
		}
		
		FileUtilCustom ioUtil = new FileUtilCustom();
		Resource resource = new ClassPathResource(ResourceConstant.mailSinaSmtpSslProperties);
		Properties properties = null;
		try {
			properties = ioUtil.getPropertiesFromFile(resource.getFile().getPath());
		} catch (IOException e) {
			e.printStackTrace();
			result.failWithMessage(ResultType.mailPropertiesError.getName());
			return result;
		}

		SendEmail sm = new SendEmail();
		sm.sendMail(
				systemConstantService.getValByName(SystemConstantStore.adminMailName), 
				systemConstantService.getValByName(SystemConstantStore.adminMailPwd), 
				Arrays.asList(sendTo),
				null,
				Arrays.asList(systemConstantService.getValByName(SystemConstantStore.adminMailName)),
				title, 
				content, 
				null,
				properties
				);
		

		result.successWithMessage(mailKey);
		return result;
	}

	@Override
	public void sendMailWithAttachment(String sendTo, String title, String content, List<String> attachmentPathList,
			Properties properties) {
		if(!isMailReady()) {
			return;
		}
		SendEmail sm = new SendEmail();
		sm.sendMail(
				systemConstantService.getValByName(SystemConstantStore.adminMailName), 
				systemConstantService.getValByName(SystemConstantStore.adminMailPwd), 
				Arrays.asList(sendTo),
				null,
				Arrays.asList(systemConstantService.getValByName(SystemConstantStore.adminMailName)),
				title, 
				content, 
				attachmentPathList,
				properties
				);
	}

	@Override
	public void sendMailWithAttachment(String sendTo, String title, String content, String attachmentPath,
			Properties properties) {
		if(!isMailReady()) {
			return;
		}
		SendEmail sm = new SendEmail();
		sm.sendMail(
				systemConstantService.getValByName(SystemConstantStore.adminMailName), 
				systemConstantService.getValByName(SystemConstantStore.adminMailPwd), 
				Arrays.asList(sendTo),
				null,
				Arrays.asList(systemConstantService.getValByName(SystemConstantStore.adminMailName)),
				title, 
				content, 
				Arrays.asList(attachmentPath),
				properties
				);
	}

	@Override
	public void sendTomcatOut() throws IOException {
		if(!isMailReady()) {
			return;
		}
		FileUtilCustom ioUtil = new FileUtilCustom();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmmss");
		String title = "日志,截止到( " + LocalDateTime.now().format(formatter) + ")";

		String tomcatOutPath = ToolPathConstant.getTomcatOutPath();

		File outputZip = new File(tomcatOutPath.replaceAll("\\.\\w{1,4}$", ".zip"));

		ioUtil.fileToZip(outputZip.getAbsolutePath(), tomcatOutPath);

		Resource resource = new ClassPathResource(ResourceConstant.mailSinaSmtpSslProperties);
		Properties properties = null;
		try {
			properties = ioUtil.getPropertiesFromFile(resource.getFile().getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		sendMailWithAttachment(systemConstantService.getValByName(SystemConstantStore.adminMailName), title, "", ToolPathConstant.getTomcatOutPath(), properties);

		outputZip.delete();
	}

	@Override
	public void sendTomcatLogFolder() throws IOException {
		if(!isMailReady()) {
			return;
		}
		FileUtilCustom ioUtil = new FileUtilCustom();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmmss");

		File logsFolder = new File(ToolPathConstant.getTomcatLogsPath());
		String zipFileName = "logs" + "(" + LocalDateTime.now().format(formatter) + ").zip";
		File outputZip = new File(logsFolder.getAbsolutePath() + "/" + zipFileName);

		List<String> filePathList = new ArrayList<String>();
		Arrays.asList(logsFolder.listFiles()).stream().filter(f -> f.isFile())
				.forEach(f -> filePathList.add(f.getAbsolutePath()));

		ioUtil.filesToZip(outputZip.getAbsolutePath(), filePathList);

//		Resource resource = new ClassPathResource(ResourceConstant.mailSinaSmtpSslProperties);
//		Properties properties = null;
//		try {
//			properties = ioUtil.getPropertiesFromFile(resource.getFile().getPath());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		sendMailWithAttachment(systemConstantService.getValByName(SystemConstantStore.adminMailName), zipFileName, zipFileName, outputZip.getAbsolutePath(),
//				properties);

		outputZip.delete();
	}

	// 暂时不再主动发送注册验证邮件,改为验证用户发送的邮件. 2018-06-28
//	@Override
//	public CommonResult sendRegistMail(Long userId, String sendTo, String nickName) {
//		CommonResult result = new CommonResult();
//		if(userId == null) {
//			result.failWithMessage(ResultType.nullParam.getName());
//			return result;
//		}
//		if(!isMailReady()) {
//			result.failWithMessage(ResultType.mailBaseOptionError.getName());
//			return result;
//		}
//		
//		String hostName = systemConstantService.getValByName(SystemConstantStore.hostName);
//		if(StringUtils.isBlank(hostName)) {
//			result.failWithMessage(ResultType.serviceError.getName());
//			return result;
//		}
//		
//		String mailKey = UUID.randomUUID().toString().replaceAll("-", "");
//		String mailUrl = hostName + UsersUrlConstant.root + UsersUrlConstant.registActivation + "?mailKey=" + mailKey;
//		
//		sendSimpleMail(userId, sendTo, "欢迎注册", createRegistMailContent(nickName, mailUrl), mailKey, MailType.registActivation);
//		
//		MailRecord mr = new MailRecord();
//		mr.setMailType(MailType.registActivation.getValue());
//		mr.setValidTime(DateUtilCustom.localDateTimeToDate(LocalDateTime.now().plusDays(3L)));
//		mr.setMailKey(mailKey);
//		mr.setUserId(userId);
//		mailRecordMapper.insertSelective(mr);
//		
//		result.successWithMessage(mailKey);
//		return result;
//		return null;
//	}
	
	// 暂时不再主动发送注册验证邮件,改为验证用户发送的邮件. 2018-06-28
//	@Override
//	public CommonResult resendRegistMail(Long userId, String sendTo, String nickName, String mailKey, String hostName) {
//		CommonResult result = new CommonResult();
//		if(userId == null) {
//			result.failWithMessage(ResultType.nullParam.getName());
//			return result;
//		}
//		if(!isMailReady()) {
//			result.failWithMessage(ResultType.mailBaseOptionError.getName());
//			return result;
//		}
//		
//		if(StringUtils.isBlank(hostName)) {
//			result.failWithMessage(ResultType.serviceError.getName());
//			return result;
//		}
//		
//		String mailUrl = hostName + UsersUrlConstant.root + UsersUrlConstant.registActivation + "?mailKey=" + mailKey;
//		
//		sendSimpleMail(userId, sendTo, "欢迎注册" + hostName, createRegistMailContent(nickName, mailUrl), mailKey, MailType.registActivation);
//		
//		Date tmpDate = DateUtilCustom.dateDiffDays(1);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		mailRecordMapper.updateResend(mailKey, sdf.format(tmpDate));
//		
//		result.successWithMessage(mailKey);
//		return result;
//	}

	@Override
	public MailRecord findMailByMailKeyMailType(String mailKey, MailType mailType) {
		if(StringUtils.isBlank(mailKey) || mailType == null) {
			return new MailRecord();
		}
		return mailRecordMapper.findMailByMailKeyMailType(mailKey, mailType.getCode());
	}

	@Override
	public MailRecord findRegistActivationUnusedByUserId(Long userId) {
		if(userId == null) {
			return new MailRecord();
		}
		return mailRecordMapper.findUnusedByUserId(userId, MailType.registActivation.getCode());
	}

	@Override
	public int updateWasUsed(Integer mailId) {
		if(mailId == null) {
			return 0;
		}
		return mailRecordMapper.updateWasUsed(mailId);
	}
	
	@Override
	public CommonResult sendForgotPasswordMail(Long userId, String email, String hostName) {
		CommonResult result = new CommonResult();
		if(userId == null || StringUtils.isBlank(email)) {
			result.fillWithResult(ResultType.nullParam);
			return result;
		}
		if(!isMailReady()) {
			result.failWithMessage(ResultType.mailBaseOptionError.getName());
			return result;
		}
		
		MailRecord oldMails = mailRecordMapper.findUnusedByUserId(userId, MailType.forgotPassword.getCode());

		String mailKey;
		String mailUrl;
		boolean hasOldMail = false;
		
		if(oldMails != null ) {
			mailKey = oldMails.getMailKey();
			hasOldMail = true;
		} else {
			mailKey = UUID.randomUUID().toString().replaceAll("-", "");
		}
		
		mailUrl = hostName + UsersUrlConstant.root + UsersUrlConstant.resetPassword + "?mailKey=" + mailKey;
		sendSimpleMail(userId, email, ("重置您在" + hostName + "的密码"), createForgotPasswordMailContent(mailUrl), mailKey, MailType.forgotPassword);
		
		MailRecord mr = new MailRecord();
		if(hasOldMail) {
			Date tmpDate = DateUtilCustom.dateDiffDays(1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			mailRecordMapper.updateResend(mailKey, sdf.format(tmpDate));
		} else {
			mr.setMailType(MailType.forgotPassword.getCode());
			mr.setValidTime(DateUtilCustom.localDateTimeToDate(LocalDateTime.now().plusDays(3L)));
			mr.setMailKey(mailKey);
			mr.setUserId(userId);
			
			mailRecordMapper.insertSelective(mr);
		}
		
		result.successWithMessage(mailKey);
		
		return result;
	}
	
	@Override
	public CommonResult sendForgotUsernameMail(String userName, String email, String hostName) {
		CommonResult result = new CommonResult();
		if(StringUtils.isBlank(userName) || StringUtils.isBlank(email)) {
			result.fillWithResult(ResultType.nullParam);
			return result;
		}
		if(!isMailReady()) {
			result.fillWithResult(ResultType.mailBaseOptionError);
			return result;
		}
		
		Long userId = userService.getUserIdByUserName(userName);
		if(userId == null) {
			result.fillWithResult(ResultType.errorParam);
			return result;
		}
		
		sendSimpleMail(userId, email, ("获取您在" + hostName + "的用户名"), ("您在" + hostName + "的用户名是: " + userName + ""), "", MailType.forgotUsername);
		
		result.successWithMessage("");
		
		return result;
	}
	
//	private String createRegistMailContent(String nickName, String mailUrl) {
//		StringBuffer content = new StringBuffer();
//		content.append("感谢注册! " + nickName + "\n");
//		content.append("十分惊喜!还在建站就遇到了您...很多功能尚在建造,招呼不周,请见谅...如果有什么建议,请联系  \n");
//		content.append("如非本人注册,请忽略此邮件 \n");
//		content.append(mailUrl + "  点击此处激活账户  或复制此链接到浏览器访问. \n");
//		content.append("再次感谢您的注册! 祝生活愉快!");
//		return content.toString();
//	}
	
	private String createForgotPasswordMailContent(String mailUrl) {
		StringBuffer content = new StringBuffer();
		content.append("收到了您重置密码的请求... \n");
		content.append("如非本人申请,请忽略此邮件 \n");
		content.append(mailUrl + "  点击此处重置密码  或复制此链接到浏览器访问. \n");
		return content.toString();
	}

	@Override
	public void sendErrorMail(String errorMessage) {
		if(!isMailReady()) {
			return;
		}
		
		FileUtilCustom ioUtil = new FileUtilCustom();
		Resource resource = new ClassPathResource(ResourceConstant.mailSinaSmtpSslProperties);
		Properties properties = null;
		try {
			properties = ioUtil.getPropertiesFromFile(resource.getFile().getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SendEmail sm = new SendEmail();
		sm.sendMail(
				systemConstantService.getValByName(SystemConstantStore.adminMailName), 
				systemConstantService.getValByName(SystemConstantStore.adminMailPwd), 
				Arrays.asList(systemConstantService.getValByName(SystemConstantStore.adminMailName)),
				null,
				null,
				("error : " + LocalDateTime.now().toString()), 
				errorMessage, 
				null,
				properties
				);
		
	}
	
	@Override
	public Message[] searchInbox(SearchTerm st) {
		if(!isMailReady()) {
			return new Message[] {};
		}
		
		MailHandle mailHandle = new MailHandle();
		FileUtilCustom ioUtil = new FileUtilCustom();

		Resource resourceSmtpProperties = new ClassPathResource(ResourceConstant.mailSinaSmtpSslProperties);
		Resource resourceImapProperties = new ClassPathResource(ResourceConstant.mailSinaImapSslProperties);
		Properties imapProperties = null;
		Properties smtpProperties = null;
		try {
			imapProperties = ioUtil.getPropertiesFromFile(resourceImapProperties.getFile().getAbsolutePath());
			smtpProperties = ioUtil.getPropertiesFromFile(resourceSmtpProperties.getFile().getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
			return new Message[] {};
		}
		
		Store store = mailHandle.getMailStore(
				systemConstantService.getValByName(SystemConstantStore.adminMailName), 
				systemConstantService.getValByName(SystemConstantStore.adminMailPwd), 
				smtpProperties, 
				imapProperties
				);
		
		Folder inbox = mailHandle.getInbox(store);
		
		Message[] targetMail = mailHandle.getMailReadOnly(inbox, st);
		
		return targetMail;
	}

	@Override
	public SearchTerm singleSearchTerm(String targetSendFrom, String targetContent, Date startDate) {
		SearchTerm searchTerm = new SearchTerm() {
			private static final long serialVersionUID = 7873209385471356176L;

			@Override
			public boolean match(Message message) {
				Date receiveDate = null;
				try {
					receiveDate = message.getReceivedDate();
				} catch (MessagingException e1) {
					e1.printStackTrace();
					return false;
				}
				if(receiveDate.before(startDate)) {
					return false;
				}
				
				Address[] from = null;
				try {
					from = message.getFrom();
				} catch (MessagingException e) {
					e.printStackTrace();
					return false;
				}
				if(from == null || from.length < 1) {
					return false;
				}
				boolean flag = false;
				for(Address f : from) {
					if(f.toString().equals(targetSendFrom)) {
						flag = true;
					}
				}
				if(!flag) {
					return false;
				}
				
				try {
					MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
					String content = mimeMultipart.getBodyPart(0).getContent().toString();
					if(content.contains(targetContent)) {
						return true;
					}
				} catch (MessagingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return false;
			}
		};
		return searchTerm;
	}
	
	@Override
	public SearchTerm searchByTargetContents(List<UserMailAndMailKeyBO> userMailAndMailKeyBOList) {
		SearchTerm searchTerm = new SearchTerm() {

			private static final long serialVersionUID = -4492471468971682840L;

			@Override
			public boolean match(Message message) {
				UserMailAndMailKeyBO bo = null;
				boolean flag = false;
				for(int i = 0; i < userMailAndMailKeyBOList.size(); i ++) {
					bo = userMailAndMailKeyBOList.get(i);
					try {
						if(message.getReceivedDate() == null) {
							continue;
						}
						LocalDateTime receivedDate = DateUtilCustom.dateToLocalDateTime(message.getReceivedDate());
						if(receivedDate == null || receivedDate.isAfter(bo.getValidTime())) {
							continue;
						}
						String content = "";
						MimeMultipart mimeMultipart = null;
						if(message.getContent().getClass().equals(String.class)) {
							content = (String) message.getContent();
						} else if(message.getContent().getClass().equals(MimeMultipart.class)) {
							mimeMultipart = (MimeMultipart) message.getContent();
							content = mimeMultipart.getBodyPart(0).getContent().toString();
						}
						if(!content.contains(bo.getMailKey())) {
							continue;
						}
						
						Address[] from = message.getFrom();
						for(Address f : from) {
							if(f.toString().equals(bo.getEmail())) {
								flag = true;
							}
						}
						return flag;
					} catch (MessagingException e1) {
						e1.printStackTrace();
						continue;
					} catch (IOException e) {
						e.printStackTrace();
						continue;
					}
				}
				return false;
			}
		};
		return searchTerm;
	}

	@Override
	public String insertNewRegistMailKey(Long userId) {
		String mailKey = UUID.randomUUID().toString().replaceAll("-", "");
		InsertNewMailRecordParam p = new InsertNewMailRecordParam();
		p.setMailType(MailType.registActivation.getCode());
		p.setValidTime(DateUtilCustom.localDateTimeToDate(LocalDateTime.now().plusDays(3L)));
		p.setMailKey(mailKey);
		p.setUserId(userId);
		if(mailRecordMapper.insertNewMailRecord(p) > 0) {
			return mailKey;
		}
		return null;
	}
}
