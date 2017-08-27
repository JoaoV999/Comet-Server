package com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions;

import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.DiceFloorItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredActionItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.events.WiredItemEvent;
import com.cometproject.server.game.rooms.objects.misc.Position;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.outgoing.room.items.SlideObjectBundleMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.items.UpdateFloorItemMessageComposer;
import com.cometproject.server.utilities.Direction;

import java.util.Random;

public class WiredActionMoveToDirection extends WiredActionItem {
    private static final int PARAM_START_DIR = 0;
    private static final int PARAM_ACTION_WHEN_BLOCKED = 1;

    private static final int ACTION_WAIT = 0;
    private static final int ACTION_TURN_RIGHT_45 = 1;
    private static final int ACTION_TURN_RIGHT_90 = 2;
    private static final int ACTION_TURN_LEFT_45 = 3;
    private static final int ACTION_TURN_LEFT_90 = 4;
    private static final int ACTION_TURN_BACK = 5;
    private static final int ACTION_TURN_RANDOM = 6;

    /**
     * The default constructor
     *
     * @param id       The ID of the item
     * @param itemId   The ID of the item definition
     * @param room     The instance of the room
     * @param owner    The ID of the owner
     * @param x        The position of the item on the X axis
     * @param y        The position of the item on the Y axis
     * @param z        The position of the item on the z axis
     * @param rotation The orientation of the item
     * @param data     The JSON object associated with this item
     */
    public WiredActionMoveToDirection(long id, int itemId, Room room, int owner, String ownerName, int x, int y, double z, int rotation, String data) {
        super(id, itemId, room, owner, ownerName, x, y, z, rotation, data);
    }

    @Override
    public boolean requiresPlayer() {
        return false;
    }

    @Override
    public int getInterface() {
        return 13;
    }

    @Override
    public void onEventComplete(WiredItemEvent event) {
        if (this.getWiredData().getParams().size() != 2) {
            return;
        }

        final int startDir = this.getWiredData().getParams().get(PARAM_START_DIR);

        synchronized (this.getWiredData().getSelectedIds()) {
            for (long itemId : this.getWiredData().getSelectedIds()) {
                RoomItemFloor floorItem = this.getRoom().getItems().getFloorItem(itemId);

                if (floorItem == null || floorItem instanceof DiceFloorItem) continue;

                if (floorItem.getMoveDirection() == -1) {
                    floorItem.setMoveDirection(startDir);
                }

                this.moveItem(floorItem, false);
            }
        }

        return;
    }

    private void attemptBlockedAction(RoomItemFloor floorItem) {
        final int actionWhenBlocked = this.getWiredData().getParams().get(PARAM_ACTION_WHEN_BLOCKED);

        if (actionWhenBlocked == 0) {
            return;
        }

        int movementDirection = floorItem.getMoveDirection();

        switch (actionWhenBlocked) {
            case ACTION_TURN_BACK:
                movementDirection = Direction.get(movementDirection).invert().num;
                break;

            case ACTION_TURN_RANDOM:
                movementDirection = Direction.random().num;
                break;

            case ACTION_TURN_RIGHT_45:
                movementDirection = this.getNextDirection(movementDirection);
                break;

            case ACTION_TURN_RIGHT_90:
                movementDirection = this.clockwise(movementDirection, 2);
                break;

            case ACTION_TURN_LEFT_45:
                movementDirection = this.getPreviousDirection(movementDirection);
                break;

            case ACTION_TURN_LEFT_90:
                movementDirection = this.antiClockwise(movementDirection, 2);
                break;

        }

        floorItem.setMoveDirection(movementDirection);
        this.moveItem(floorItem, true);
    }

    private void moveItem(RoomItemFloor floorItem, boolean secondAttempt) {
        final Position currentPosition = floorItem.getPosition().copy();
        final Position nextPosition = floorItem.getPosition().squareInFront(floorItem.getMoveDirection());

        if (this.getRoom().getItems().moveFloorItem(floorItem.getId(), floorItem.getPosition().squareInFront(floorItem.getMoveDirection()), floorItem.getRotation(), true)) {
            nextPosition.setZ(floorItem.getPosition().getZ());
            this.getRoom().getEntities().broadcastMessage(new SlideObjectBundleMessageComposer(currentPosition, nextPosition, this.getVirtualId(), 0, floorItem.getVirtualId()));
        } else {
            if (!secondAttempt)
                this.attemptBlockedAction(floorItem);
        }
    }

    private int clockwise(int movementDirection, int times) {
        for(int i = 0; i < times; i++) {
            movementDirection = this.getNextDirection(movementDirection);
        }

        return movementDirection;
    }

    private int antiClockwise(int movementDirection, int times) {
        for(int i = 0; i < times; i++) {
            movementDirection = this.getPreviousDirection(movementDirection);
        }

        return movementDirection;
    }

    private int getNextDirection(int movementDirection) {
        if(movementDirection == 7) {
            return 0;
        }

        return movementDirection + 1;
    }

    private int getPreviousDirection(int movementDirection) {
        if(movementDirection == 0) {
            return 7;
        }

        return movementDirection -1;
    }
}
