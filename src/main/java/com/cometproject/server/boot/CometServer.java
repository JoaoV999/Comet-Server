package com.cometproject.server.boot;

import com.cometproject.server.cache.CometCache;
import com.cometproject.server.config.CometSettings;
import com.cometproject.server.config.Configuration;
import com.cometproject.server.config.Locale;
import com.cometproject.server.game.CometManager;
import com.cometproject.server.game.GameThread;
import com.cometproject.server.logging.LogManager;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.plugins.PluginManager;
import com.cometproject.server.storage.StorageManager;
import com.cometproject.server.storage.helpers.SqlIndexChecker;
import com.cometproject.server.tasks.CometThreadManagement;

public class CometServer {
    private Configuration config;

    private CometThreadManagement threadManagement;

    private StorageManager storageManager;
    private PluginManager pluginManager;
    private NetworkManager networkManager;

    private LogManager loggingManager;

    public CometServer() {
    }

    public void init() {
        /*
            could probs move all this stuff to singleton and get rid of this object completely
         */

        loadConfig();

        threadManagement = new CometThreadManagement();
        storageManager = new StorageManager();
        pluginManager = new PluginManager();

        loggingManager = new LogManager();

        SqlIndexChecker.checkIndexes(storageManager);
        Locale.init();
        CometManager.init();
        CometCache.create();

        networkManager = new NetworkManager(this.getConfig().get("comet.network.host"), this.getConfig().get("comet.network.port"));
        CometManager.gameThread = new GameThread(threadManagement);

        if (Comet.isDebugging) {
            CometManager.getLogger().debug("Comet Server is debugging");
        }
    }

    public void loadConfig() {
        config = new Configuration("./config/comet.properties");
        CometSettings.set(config.getProperties());
    }

    public Configuration getConfig() {
        return this.config;
    }

    public StorageManager getStorage() {
        return this.storageManager;
    }

    public NetworkManager getNetwork() {
        return this.networkManager;
    }

    public CometThreadManagement getThreadManagement() {
        return this.threadManagement;
    }

    public PluginManager getPlugins() {
        return pluginManager;
    }

    public LogManager getLoggingManager() {
        return loggingManager;
    }
}
