package cn.kim.common.shiro.quartz;

import cn.kim.util.AuthcUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Collection;

/**
 * 基于Quartz 2.* 版本的实现
 */
public class QuartzSessionValidationJob extends QuartzJobBean {

    /**
     * Key used to store the session manager in the job data map for this job.
     */
    public static final String SESSION_MANAGER_KEY = "sessionManager";

    /*--------------------------------------------
    |    I N S T A N C E   V A R I A B L E S    |
    ============================================*/
    private static final Logger log = LoggerFactory.getLogger(QuartzSessionValidationJob.class);

    /*--------------------------------------------
    |         C O N S T R U C T O R S           |
    ============================================*/

    /*--------------------------------------------
    |  A C C E S S O R S / M O D I F I E R S    |
    ============================================*/

    /*--------------------------------------------
    |               M E T H O D S               |
    ============================================*/

    /**
     * Called when the job is executed by quartz. This method delegates to the <tt>validateSessions()</tt> method on the
     * associated session manager.
     *
     * @param context the Quartz job execution context for this execution.
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        ValidatingSessionManager sessionManager = (ValidatingSessionManager) jobDataMap.get(SESSION_MANAGER_KEY);

        if (log.isDebugEnabled()) {
            log.debug("Executing session validation Quartz job...");
        }

        sessionManager.validateSessions();
        if (log.isDebugEnabled()) {
            log.debug("Session validation Quartz job complete.");
        }
    }

}