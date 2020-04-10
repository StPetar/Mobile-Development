package com.example.madcampusmap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RoomsDB {

    private final static Room[] ROOMS = {
            new Room("A", 100),
            new Room("A", 101),
            new Room("A", 102),
            new Room("A", 103),
            new Room("A", 104),
            new Room("A", 105),
            new Room("B", 100),
            new Room("B", 101),
            new Room("B", 102),
            new Room("B", 103),
            new Room("B", 104),
            new Room("B", 105),
            new Room("C", 100),
            new Room("C", 101),
            new Room("C", 102),
            new Room("C", 103),
            new Room("C", 104),
            new Room("C", 105),
            new Room("D", 100),
            new Room("D", 101),
            new Room("D", 102),
            new Room("D", 103),
            new Room("D", 104),
            new Room("D", 105),
            new Room("E", 100),
            new Room("E", 101),
            new Room("E", 102),
            new Room("E", 103),
            new Room("E", 104),
            new Room("E", 105),
            new Room("F", 100),
            new Room("F", 101),
            new Room("F", 102),
            new Room("F", 103),
            new Room("F", 104),
            new Room("F", 105)
    };

    private final static Map<String, Room> ROOM_MAP = new HashMap<>();

    private final static String[] ROOM_LIST;

    static {
        //fill hmap
        for (Room r : ROOMS) {
            ROOM_MAP.put(r.getRoom(), r);
        }

        ROOM_LIST = new String[ROOM_MAP.size()];
        ROOM_MAP.keySet().toArray(ROOM_LIST);
        Arrays.sort(ROOM_LIST);

    }

    public String[] getRooms() {
        return ROOM_LIST;
    }

}
