package com.cometproject.server.network.messages.outgoing.room.permissions;

import com.cometproject.server.network.messages.headers.Composers;
import com.cometproject.server.network.messages.types.Composer;

public class GivePowersMessageComposer {
    public static Composer compose(int roomId, int userId) {
        Composer msg = new Composer(Composers.GivePowersMessageComposer);

        msg.writeInt(roomId);
        msg.writeInt(userId);

        return msg;
    }
}