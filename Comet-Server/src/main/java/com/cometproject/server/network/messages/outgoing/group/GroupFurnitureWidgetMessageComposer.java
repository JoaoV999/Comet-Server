package com.cometproject.server.network.messages.outgoing.group;

import com.cometproject.server.network.messages.composers.MessageComposer;
import com.cometproject.server.network.messages.headers.Composers;
import com.cometproject.server.network.messages.types.Composer;


public class GroupFurnitureWidgetMessageComposer extends MessageComposer {
    private final int furnitureId;
    private final int groupId;
    private final String groupTitle;
    private final int homeRoom;
    private final boolean isMember;
    private final boolean hasForum;

    public GroupFurnitureWidgetMessageComposer(final int furnitureId, final int groupId, final String groupTitle, final int homeRoom, final boolean isMember, final boolean hasForum) {
        this.furnitureId = furnitureId;
        this.groupId = groupId;
        this.groupTitle = groupTitle;
        this.homeRoom = homeRoom;
        this.isMember = isMember;
        this.hasForum = hasForum;
    }

    @Override
    public short getId() {
        return Composers.GroupFurnitureWidgetMessageComposer;
    }

    @Override
    public void compose(Composer msg) {
        msg.writeInt(furnitureId);
        msg.writeInt(groupId);
        msg.writeString(groupTitle);
        msg.writeInt(homeRoom);
        msg.writeBoolean(isMember);
        msg.writeBoolean(hasForum);
    }
}