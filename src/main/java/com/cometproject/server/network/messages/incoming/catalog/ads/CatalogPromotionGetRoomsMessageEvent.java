package com.cometproject.server.network.messages.incoming.catalog.ads;

import com.cometproject.server.game.CometManager;
import com.cometproject.server.game.rooms.types.RoomData;
import com.cometproject.server.network.messages.incoming.IEvent;
import com.cometproject.server.network.messages.outgoing.catalog.ads.CatalogPromotionGetRoomsMessageComposer;
import com.cometproject.server.network.messages.types.Event;
import com.cometproject.server.network.sessions.Session;
import com.google.common.collect.Lists;

import java.util.List;

public class CatalogPromotionGetRoomsMessageEvent implements IEvent {
    @Override
    public void handle(Session client, Event msg) throws Exception {

        List<RoomData> roomDataList = Lists.newArrayList();

        for(Integer roomId : client.getPlayer().getRooms()) {
            RoomData data = CometManager.getRooms().getRoomData(roomId);

            if(data != null && !CometManager.getRooms().hasPromotion(roomId)) {
                roomDataList.add(data);
            }
        }

        client.send(CatalogPromotionGetRoomsMessageComposer.compose(roomDataList));
    }
}