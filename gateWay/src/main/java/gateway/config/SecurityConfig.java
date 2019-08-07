package gateway.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;

import gateway.base.user.pojo.type.RolesType;
import gateway.base.user.service.impl.CustomAuthenticationFailHandler;
import gateway.base.user.service.impl.CustomAuthenticationSuccessHandler;
import gateway.base.user.service.impl.CustomUserDetailsService;
import gateway.config.customComponent.CustomAuthenticationProvider;
import gateway.config.customComponent.CustomPasswordEncoder;
import gateway.config.customComponent.LimitLoginAuthenticationProvider;
import scAppCommon.constant.url.AdminUrlConstant;
import scAppCommon.constant.url.ArticleAdminCommentUrlConstant;
import scAppCommon.constant.url.ArticleAdminUrlConstant;
import scAppCommon.constant.url.ArticleUrlConstant;
import scAppCommon.constant.url.FakeFTPUrlConstant;
import scAppCommon.constant.url.LoginUrlConstant;
import scAppCommon.constant.url.ToolUrlConstant;
import scAppCommon.constant.url.UploadUrlConstant;
import scAppCommon.constant.url.UsersUrlConstant;
import scAppCommon.constant.url.WXUrl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;

	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Autowired
	private CustomAuthenticationFailHandler customAuthenticationFailHandler;

	@Autowired
    private CustomAuthenticationProvider authProvider;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
	    DaoAuthenticationProvider authProvider = new LimitLoginAuthenticationProvider();
	    authProvider.setUserDetailsService(customUserDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/welcome**").permitAll()
            .antMatchers(LoginUrlConstant.login + "/**").permitAll()
            .antMatchers(UsersUrlConstant.root + "/**").permitAll()
            .antMatchers("/static_resources/**").permitAll()
            .antMatchers("/tHome/**").permitAll()
            .antMatchers(WXUrl.root + WXUrl.weixin).permitAll()
//            .anyRequest().authenticated() 
            // used to allow anonymous access 
            // .antMatchers("/welcome**").access("IS_AUTHENTICATED_ANONYMOUSLY")
//            .antMatchers(ArticleUrlConstant.root + "/**").access("hasAnyRole('" + RolesType.ROLE_ADMIN.getRoleName() + "','" + RolesType.ROLE_USER.getRoleName() + "')")
            .antMatchers("/holder/**")
            	.access(hasAnyRole(RolesType.ROLE_SUPER_ADMIN, RolesType.ROLE_USER))
            .antMatchers("/accountInfo/**")
            	.access(hasAnyRole(RolesType.ROLE_ADMIN, RolesType.ROLE_USER))
            .antMatchers(AdminUrlConstant.root + "/**")
            	.access(hasAnyRole(RolesType.ROLE_SUPER_ADMIN))
            .antMatchers(FakeFTPUrlConstant.root + "/**")
            	.access(hasRole(RolesType.ROLE_SUPER_ADMIN))
            .antMatchers(ArticleAdminUrlConstant.root + "/**")
            	.access(hasAnyRole(RolesType.ROLE_ADMIN, RolesType.ROLE_SUPER_ADMIN)) 
            .antMatchers(ArticleUrlConstant.root + ArticleUrlConstant.createArticleLong)
            	.access(hasAnyRole(RolesType.ROLE_SUPER_ADMIN, RolesType.ROLE_ADMIN, RolesType.ROLE_DEV, RolesType.ROLE_USER))
            .antMatchers(ArticleUrlConstant.root + ArticleUrlConstant.creatingArticleLong)
            	.access(hasAnyRole(RolesType.ROLE_SUPER_ADMIN, RolesType.ROLE_ADMIN, RolesType.ROLE_DEV, RolesType.ROLE_USER))
            .antMatchers(ArticleAdminCommentUrlConstant.root + "/**")
            	.access(hasAnyRole(RolesType.ROLE_SUPER_ADMIN, RolesType.ROLE_ADMIN)) 
            .antMatchers("/dba/**")
            	.access(hasAnyRole(RolesType.ROLE_SUPER_ADMIN, RolesType.ROLE_DBA)) 
            .antMatchers(ToolUrlConstant.root + "/**")
            	.access(hasRole(RolesType.ROLE_SUPER_ADMIN))
//            	TODO dev mark
//            .antMatchers("/test/**")
//            	.access(hasRole(RolesType.ROLE_SUPER_ADMIN))
            .antMatchers(UploadUrlConstant.uploadPriRoot + "/**")
            	.access(hasAnyRole(RolesType.ROLE_SUPER_ADMIN, RolesType.ROLE_DEV))
            .antMatchers(WXUrl.root + "/**")
            	.access(hasAnyRole(RolesType.ROLE_SUPER_ADMIN, RolesType.ROLE_DEV))
            .and()
				.formLogin().loginPage("/login/login").failureUrl("/login/login?error")
				.loginProcessingUrl("/auth/login_check")
				.successHandler(customAuthenticationSuccessHandler)
				.failureHandler(customAuthenticationFailHandler)
				.usernameParameter("user_name").passwordParameter("pwd")
			.and()
		    	.logout().logoutRequestMatcher(new AntPathRequestMatcher("/login/logout"))
		    .and()
		    	.exceptionHandling().accessDeniedPage("/403")
		    .and()
		    	.rememberMe().tokenRepository(persistentTokenRepository())
		    	.tokenValiditySeconds(3600)
		    .and()
		    	.authorizeRequests()
		    .and()
		    	.csrf()
//		    尝试搭建 web socket, 修改同源策略
//		    .and()
//		        .headers().frameOptions().sameOrigin()
		    ;
	  
        /*
         * 增加filter在此  同样操作 但建议使用
         * http.addFilterAfter(newFilter,CsrfFilter.class); 
         */
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        http.addFilterBefore(encodingFilter, CsrfFilter.class);
        
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring()
	    .antMatchers("/test/testIgnoring")
	    ;
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}

	@Bean
	public CustomPasswordEncoder passwordEncoder(){
		return new CustomPasswordEncoder();
	}
	
	private String hasRole(RolesType roleType) {
		if(roleType == null) {
			return "hasRole('')";
		}
		
		return "hasRole('"+ roleType.getName() +"')";
	}
	
	private String hasAnyRole(RolesType... roleTypes) {
		if(roleTypes == null) {
			return "hasRole('')";
		}
		
		StringBuffer roleExpressionBuilder = new StringBuffer("hasAnyRole(");
		
		for(RolesType roleType : roleTypes) {
			if(roleType != null) {
				roleExpressionBuilder.append("'" + roleType.getName() + "',");
			}
		}
		roleExpressionBuilder.replace(
				roleExpressionBuilder.length()-1, 
				roleExpressionBuilder.length(), 
				"");
		roleExpressionBuilder.append(")");
		return roleExpressionBuilder.toString();
	}
}
