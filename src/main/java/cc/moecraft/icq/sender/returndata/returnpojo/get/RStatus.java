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
    private Boolean appEnabled;

    @SerializedName("app_good")
    @Expose
    private Boolean appGood;

    @SerializedName("app_initialized")
    @Expose
    private Boolean appInitialized;

    @SerializedName("good")
    @Expose
    private Boolean good;

    @SerializedName("online")
    @Expose
    private Boolean online;

    @SerializedName("plugins_good")
    @Expose
    private PluginsGood pluginsGood;

    @Data
    @Setter(AccessLevel.NONE)
    public class PluginsGood
    {
        @SerializedName("async_actions")
        @Expose
        private Boolean asyncActions;

        @SerializedName("backward_compatibility")
        @Expose
        private Boolean backwardCompatibility;

        @SerializedName("default_config_generator")
        @Expose
        private Boolean defaultConfigGenerator;

        @SerializedName("event_data_patcher")
        @Expose
        private Boolean eventDataPatcher;

        @SerializedName("event_filter")
        @Expose
        private Boolean eventFilter;

        @SerializedName("experimental_actions")
        @Expose
        private Boolean experimentalActions;

        @SerializedName("extension_loader")
        @Expose
        private Boolean extensionLoader;

        @SerializedName("heartbeat_generator")
        @Expose
        private Boolean heartbeatGenerator;

        @SerializedName("http")
        @Expose
        private Boolean http;

        @SerializedName("ini_config_loader")
        @Expose
        private Boolean iniConfigLoader;

        @SerializedName("json_config_loader")
        @Expose
        private Boolean jsonConfigLoader;

        @SerializedName("loggers")
        @Expose
        private Boolean loggers;

        @SerializedName("message_enhancer")
        @Expose
        private Boolean messageEnhancer;

        @SerializedName("post_message_formatter")
        @Expose
        private Boolean postMessageFormatter;

        @SerializedName("rate_limited_actions")
        @Expose
        private Boolean rateLimitedActions;

        @SerializedName("restarter")
        @Expose
        private Boolean restarter;

        @SerializedName("updater")
        @Expose
        private Boolean updater;

        @SerializedName("websocket")
        @Expose
        private Boolean websocket;

        @SerializedName("websocket_reverse")
        @Expose
        private Boolean websocketReverse;

        @SerializedName("worker_pool_resizer")
        @Expose
        private Boolean workerPoolResizer;
    }
}
