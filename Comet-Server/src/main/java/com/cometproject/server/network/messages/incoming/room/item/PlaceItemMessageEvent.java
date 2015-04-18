package com.cometproject.server.network.messages.incoming.room.item;

import com.cometproject.server.game.players.components.types.inventory.InventoryItem;
import com.cometproject.server.game.rooms.objects.misc.Position;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.notification.RoomNotificationMessageComposer;
import com.cometproject.server.network.messages.types.MessageEvent;
import com.cometproject.server.network.sessions.Session;
import com.google.common.collect.Maps;

import java.util.Map;


public class PlaceItemMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) {
        if (client.getPlayer() == null || client.getPlayer().getEntity() == null) {
            return;
        }

        String data = msg.readString();

        if(data == null) return;

        String[] parts = data.split(" ");
        int id = Integer.parseInt(parts[0].replace("-", ""));

        if (!client.getPlayer().getEntity().getRoom().getRights().hasRights(client.getPlayer().getId())
                && !client.getPlayer().getPermissions().hasPermission("room_full_control")) {
            Map<String, String> notificationParams = Maps.newHashMap();

            notificationParams.put("message", "${room.error.cant_set_not_owner}");

            client.send(new RoomNotificationMessageComposer("furni_placement_error", notificationParams));
            return;
        }

        try {
            if (parts.length > 1 && parts[1].startsWith(":")) {
                String position = Position.validateWallPosition(parts[1] + " " + parts[2] + " " + parts[3]);

                if (position == null) {
                    return;
                }

                InventoryItem item = client.getPlayer().getInventory().getWallItem(id);

                if (item == null) {
                    return;
                }

                client.getPlayer().getEntity().getRoom().getItems().placeWallItem(item, position, client.getPlayer());
            } else {
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);
                int rot = Integer.parseInt(parts[3]);

                InventoryItem item = client.getPlayer().getInventory().getFloorItem(id);

                if (item == null) {
                    return;
                }

                client.getPlayer().getEntity().getRoom().getItems().placeFloorItem(item, x, y, rot, client.getPlayer());
            }
        } catch (Exception e) {
            client.getLogger().error("Error while placing item", e);
        }
    }
}