package com.cometproject.server.game.players.components.types;

import java.sql.ResultSet;
import java.sql.SQLException;


public class InventoryBot {
    private int id, ownerId;
    private String name, figure, gender, motto, ownerName, mode, type;

    public InventoryBot(ResultSet data) throws SQLException {
        this.id = data.getInt("id");
        this.ownerId = data.getInt("owner_id");

        this.name = data.getString("name");
        this.figure = data.getString("figure");
        this.gender = data.getString("gender");
        this.motto = data.getString("motto");
        this.ownerName = data.getString("owner");
        this.mode = data.getString("mode");
        this.type = data.getString("type");
    }

    public InventoryBot(int id, int ownerId, String ownerName, String name, String figure, String gender, String motto, String type) {
        this.id = id;
        this.ownerId = ownerId;

        this.name = name;
        this.figure = figure;
        this.gender = gender;
        this.motto = motto;
        this.ownerName = ownerName;
        this.mode = "default";
        this.type = type;
    }

    public int getId() {
        return this.id;
    }

    public int getOwnerId() {
        return this.ownerId;
    }

    public String getName() {
        return this.name;
    }

    public String getFigure() {
        return this.figure;
    }

    public String getGender() {
        return this.gender;
    }

    public String getMotto() {
        return this.motto;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public String getMode() {
        return mode;
    }

    public String getType() {
        return type;
    }
}
