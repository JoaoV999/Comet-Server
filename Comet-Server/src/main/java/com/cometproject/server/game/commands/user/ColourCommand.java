package com.cometproject.server.game.commands.user;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.game.rooms.types.components.types.ChatMessageColour;
import com.cometproject.server.network.sessions.Session;

public class ColourCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) {
            client.getPlayer().setChatMessageColour(null);
        }

        final String colourName = params[0];

        try {
            final ChatMessageColour colour = ChatMessageColour.valueOf(colourName);

            client.getPlayer().setChatMessageColour(colour);

            sendNotif(Locale.getOrDefault(
                    "command.colour.done",
                    "Your chat messages are now %colour%").replace("%colour%", colour.toString().toLowerCase()), client);
        } catch (Exception e) {
            sendNotif(Locale.getOrDefault(
                    "command.colour.invalid",
                    "Invalid colour, available colours: %colours%").replace("%colours%",ChatMessageColour.getAllAvailable()), client);
        }
    }

    @Override
    public String getPermission() {
        return "colour_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.parameter.colour", "%colour%");
    }

    @Override
    public String getDescription() {
        return Locale.getOrDefault("command.colour.description", "Allows you to change the colour of your chat messages");
    }
}
