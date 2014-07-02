package com.cometproject.server.game.commands.vip;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.messages.outgoing.room.avatar.UpdateInfoMessageComposer;
import com.cometproject.server.network.sessions.Session;
import org.apache.commons.lang.StringUtils;

public class NoFaceCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        String figure = client.getPlayer().getData().getFigure();

        if(figure.contains("hd-")) {
            String[] head = ("hd-" + figure.split("hd-")[1].split("\\.")[0]).split("-");

            client.getPlayer().getData().setFigure(figure.replace(StringUtils.join(head, "-"), "hd-" + 99999 + "-" + head[2]));

            client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(UpdateInfoMessageComposer.compose(client.getPlayer().getEntity()));
            client.send(UpdateInfoMessageComposer.compose(true, client.getPlayer().getEntity()));

            client.getPlayer().getData().save();
        }
    }

    @Override
    public String getPermission() {
        return "noface_command";
    }

    @Override
    public String getDescription() {
        return Locale.get("command.noface.description");
    }
}