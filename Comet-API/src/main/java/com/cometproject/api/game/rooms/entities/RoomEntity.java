package com.cometproject.api.game.rooms.entities;

import com.cometproject.api.game.utilities.Position;

public interface RoomEntity {
    int getId();

    Position getPosition();

    int getHeadRotation();

    int getBodyRotation();
}
