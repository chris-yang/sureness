package com.usthe.sureness;

import com.usthe.sureness.matcher.DefaultPathRoleMatcher;
import com.usthe.sureness.mgt.SurenessSecurityManager;
import com.usthe.sureness.processor.DefaultProcessorManager;
import com.usthe.sureness.processor.Processor;
import com.usthe.sureness.processor.support.JwtProcessor;
import com.usthe.sureness.processor.support.NoneProcessor;
import com.usthe.sureness.processor.support.PasswordProcessor;
import com.usthe.sureness.provider.DocumentResourceDefaultProvider;
import com.usthe.sureness.subject.SubjectFactory;
import com.usthe.sureness.subject.support.WebSubjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/** 对用文件做持久层权限资源方式的默认配置
 * @author tomsun28
 * @date 11:26 2019-05-26
 */
public class DefaultSurenessConfig {

    private static final Logger logger = LoggerFactory.getLogger(DefaultSurenessConfig.class);

    private DefaultSurenessConfig() {
        this.init();
    }

    private void init() {
        // resource init
        DocumentResourceDefaultProvider resourceProvider = new DocumentResourceDefaultProvider();
        if (logger.isDebugEnabled()) {
            logger.debug("DocumentResourceDefaultProvider init");
        }

        // process init
        List<Processor> processorList = new LinkedList<>();
        NoneProcessor noneProcessor = new NoneProcessor();
        processorList.add(noneProcessor);
        JwtProcessor jwtProcessor = new JwtProcessor();
        processorList.add(jwtProcessor);
        PasswordProcessor passwordProcessor = new PasswordProcessor();
        passwordProcessor.setAccountProvider(resourceProvider);
        processorList.add(passwordProcessor);
        DefaultProcessorManager processorManager = new DefaultProcessorManager(processorList);
        if (logger.isDebugEnabled()) {
            logger.debug("DefaultProcessorManager init");
        }

        // pathRoleMatcher init
        DefaultPathRoleMatcher pathRoleMatcher = DefaultPathRoleMatcher.getInstance();
        pathRoleMatcher.setPathTreeProvider(resourceProvider);
        pathRoleMatcher.buildTree();
        if (logger.isDebugEnabled()) {
            logger.debug("DefaultPathRoleMatcher init");
        }

        // surenessSecurityManager init
        SurenessSecurityManager securityManager = SurenessSecurityManager.getInstance();
        securityManager.setPathRoleMatcher(pathRoleMatcher);
        SubjectFactory subjectFactory = new WebSubjectFactory();
        securityManager.setSubjectFactory(subjectFactory);
        securityManager.setProcessorManager(processorManager);
        if (logger.isDebugEnabled()) {
            logger.debug("SurenessSecurityManager init");
        }
    }

    /**
     * 单例静态内部类
     */
    public static class SingleDefaultSurenessConfig {
        private static final DefaultSurenessConfig INSTANCE = new DefaultSurenessConfig();
    }

    public static DefaultSurenessConfig getInstance() {
        return SingleDefaultSurenessConfig.INSTANCE;
    }
}