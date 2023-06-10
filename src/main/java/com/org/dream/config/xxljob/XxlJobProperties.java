package com.org.dream.config.xxljob;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * XXL-Job 配置类
 */
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {

    /**
     * 是否开启，默认为 true 关闭
     */
    private Boolean enabled = true;
    /**
     * 访问令牌
     */
    private String accessToken;
    /**
     * 控制器配置
     */
    private AdminProperties admin;
    /**
     * 执行器配置
     */
    private ExecutorProperties executor;

    /**
     * Gets enabled.
     * @return the enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Sets enabled.
     * @param enabled the enabled
     */
    public void setEnabled(Boolean enabled) {
        if (enabled != null) {
            this.enabled = enabled;
        }
    }

    /**
     * Gets access token.
     * @return the access token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets access token.
     * @param accessToken the access token
     */
    public void setAccessToken(String accessToken) {
        if (accessToken != null && accessToken.trim().length() > 0) {
            this.accessToken = accessToken;
        }
    }

    /**
     * Gets admin.
     * @return the admin
     */
    public AdminProperties getAdmin() {
        return admin;
    }

    /**
     * Sets admin.
     * @param admin the admin
     */
    public void setAdmin(AdminProperties admin) {
        this.admin = admin;
    }

    /**
     * Gets executor.
     * @return the executor
     */
    public ExecutorProperties getExecutor() {
        return executor;
    }

    /**
     * Sets executor.
     * @param executor the executor
     */
    public void setExecutor(ExecutorProperties executor) {
        this.executor = executor;
    }

    /**
     * XXL-Job 调度器配置类
     */
    public static class AdminProperties {

        /**
         * 调度器地址
         */
        private String addresses;

        /**
         * Gets addresses.
         * @return the addresses
         */
        public String getAddresses() {
            return addresses;
        }

        /**
         * Sets addresses.
         * @param addresses the addresses
         */
        public void setAddresses(String addresses) {
            this.addresses = addresses;
        }

        @Override
        public String toString() {
            return "AdminProperties{" +
                    "addresses='" + addresses + '\'' +
                    '}';
        }

    }

    /**
     * XXL-Job 执行器配置类
     */
    public static class ExecutorProperties {

        /**
         * 默认端口
         * <p>
         * 这里使用 -1 表示随机
         */
        private static final Integer PORT_DEFAULT = -1;

        /**
         * 默认日志保留天数
         * <p>
         * 默认为 -1，不清理，永久保留
         */
        private static final Integer LOG_RETENTION_DAYS_DEFAULT = -1;

        /**
         * 应用名
         */
        private String appName;
        /**
         * 执行器的 IP
         */
        private String ip;
        /**
         * 执行器的 Port
         */
        private Integer port = PORT_DEFAULT;
        /**
         * 日志地址
         */
        private String logPath;
        /**
         * 日志保留天数
         */
        private Integer logRetentionDays = LOG_RETENTION_DAYS_DEFAULT;

        /**
         * Gets app name.
         * @return the app name
         */
        public String getAppName() {
            return appName;
        }

        /**
         * Sets app name.
         * @param appName the app name
         */
        public void setAppName(String appName) {
            this.appName = appName;
        }

        /**
         * Gets log path.
         * @return the log path
         */
        public String getLogPath() {
            return logPath;
        }

        /**
         * Sets log path.
         * @param logPath the log path
         */
        public void setLogPath(String logPath) {
            this.logPath = logPath;
        }

        /**
         * Gets ip.
         * @return the ip
         */
        public String getIp() {
            return ip;
        }

        /**
         * Sets ip.
         * @param ip the ip
         */
        public void setIp(String ip) {
            this.ip = ip;
        }

        /**
         * Gets port.
         * @return the port
         */
        public Integer getPort() {
            return port;
        }

        /**
         * Sets port.
         * @param port the port
         */
        public void setPort(Integer port) {
            this.port = port;
        }

        /**
         * Gets log retention days.
         * @return the log retention days
         */
        public Integer getLogRetentionDays() {
            return logRetentionDays;
        }

        /**
         * Sets log retention days.
         * @param logRetentionDays the log retention days
         */
        public void setLogRetentionDays(Integer logRetentionDays) {
            this.logRetentionDays = logRetentionDays;
        }
    }

}
