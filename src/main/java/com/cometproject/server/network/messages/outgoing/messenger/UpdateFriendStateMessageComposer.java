package com.cometproject.server.network.messages.outgoing.messenger;

import com.cometproject.server.game.players.data.PlayerAvatar;
import com.cometproject.server.game.players.data.PlayerData;
import com.cometproject.server.network.messages.headers.Composers;
import com.cometproject.server.network.messages.types.Composer;


public class UpdateFriendStateMessageComposer {
    public static Composer compose(int action, int userId) {
        Composer msg = new Composer(Composers.FriendUpdateMessageComposer);

        msg.writeInt(0);
        msg.writeInt(1);
        msg.writeInt(action);
        msg.writeInt(userId);

        return msg;
    }

    public static Composer compose(PlayerAvatar request, boolean online, boolean inRoom) {
        return compose(request.getId(), request.getUsername(), request.getFigure(), request.getMotto(), online, inRoom);
    }

    public static Composer compose(PlayerData player, boolean online, boolean inRoom) {
        return compose(player.getId(), player.getUsername(), player.getFigure(), player.getMotto(), online, inRoom);
    }

    public static Composer compose(int id, String username, String figure, String motto, boolean online, boolean inRoom) {
        Composer msg = new Composer(Composers.FriendUpdateMessageComposer);

        msg.writeInt(0);
        msg.writeInt(1);
        msg.writeInt(0);
        msg.writeInt(id);
        msg.writeString(username);
        msg.writeInt(1);
        msg.writeBoolean(online);
        msg.writeBoolean(inRoom);
        msg.writeString(figure);
        msg.writeInt(0);
        msg.writeString(motto);
        msg.writeString(""); // facebook name ?
        msg.writeString("");
        msg.writeBoolean(true);
        msg.writeBoolean(true);
        msg.writeBoolean(false);
        msg.writeBoolean(false);
        msg.writeBoolean(false);

        return msg;
    }
}
