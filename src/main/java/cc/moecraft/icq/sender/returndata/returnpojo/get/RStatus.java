package cc.moecraft.icq.sender.returndata.returnpojo.get;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * 获取插件运行状态
 *
 * @author Hykilpikonna
 */
@Data
@Setter(AccessLevel.NONE)
public class RStatus implements ReturnPojoBase
{
    @SerializedName("app_enabled")
    @Expose
    public Boolean appEnabled;

    @SerializedName("app_good")
    @Expose
    public Boolean appGood;

    @SerializedName("app_initialized")
    @Expose
    public Boolean appInitialized;

    @SerializedName("good")
    @Expose
    public Boolean good;

    @SerializedName("online")
    @Expose
    public Boolean online;

    @SerializedName("plugins_good")
    @Expose
    public PluginsGood pluginsGood;

    @Data
    @Setter(AccessLevel.NONE)
    public class PluginsGood
    {
        @SerializedName("async_actions")
        @Expose
        public Boolean asyncActions;

        @SerializedName("backward_compatibility")
        @Expose
        public Boolean backwardCompatibility;

        @SerializedName("default_config_generator")
        @Expose
        public Boolean defaultConfigGenerator;

        @SerializedName("event_data_patcher")
        @Expose
        public Boolean eventDataPatcher;

        @SerializedName("event_filter")
        @Expose
        public Boolean eventFilter;

        @SerializedName("experimental_actions")
        @Expose
        public Boolean experimentalActions;

        @SerializedName("extension_loader")
        @Expose
        public Boolean extensionLoader;

        @SerializedName("heartbeat_generator")
        @Expose
        public Boolean heartbeatGenerator;

        @SerializedName("http")
        @Expose
        public Boolean http;

        @SerializedName("ini_config_loader")
        @Expose
        public Boolean iniConfigLoader;

        @SerializedName("json_config_loader")
        @Expose
        public Boolean jsonConfigLoader;

        @SerializedName("loggers")
        @Expose
        public Boolean loggers;

        @SerializedName("message_enhancer")
        @Expose
        public Boolean messageEnhancer;

        @SerializedName("post_message_formatter")
        @Expose
        public Boolean postMessageFormatter;

        @SerializedName("rate_limited_actions")
        @Expose
        public Boolean rateLimitedActions;

        @SerializedName("restarter")
        @Expose
        public Boolean restarter;

        @SerializedName("updater")
        @Expose
        public Boolean updater;

        @SerializedName("websocket")
        @Expose
        public Boolean websocket;

        @SerializedName("websocket_reverse")
        @Expose
        public Boolean websocketReverse;

        @SerializedName("worker_pool_resizer")
        @Expose
        public Boolean workerPoolResizer;
    }
}
