package com.cometproject.server.network.messages.incoming.room.moderation;

import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.misc.settings.RoomMuteState;
import com.cometproject.server.network.messages.incoming.IEvent;
import com.cometproject.server.network.messages.types.Event;
import com.cometproject.server.network.sessions.Session;

public class MutePlayerMessageEvent implements IEvent {
    @Override
    public void handle(Session client, Event msg) throws Exception {
        int playerId = msg.readInt();
        int unk = msg.readInt();
        int lengthMinutes = msg.readInt();

        if(client.getPlayer().getEntity() == null || client.getPlayer().getEntity().getRoom() == null) {
            return;
        }

        final Room room = client.getPlayer().getEntity().getRoom();

        if (client.getPlayer().getId() != room.getData().getOwnerId() && room.getData().getMuteState() != RoomMuteState.RIGHTS && !client.getPlayer().getPermissions().hasPermission("room_full_control")) {
            return;
        }

        if(playerId == room.getData().getOwnerId()) {
            return;
        }

        room.getRights().addMute(playerId, lengthMinutes);
    }
}