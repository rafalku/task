package com.ing.task;

import lombok.Getter;

import java.util.ArrayList;

@Getter
class Clan {
    private int numberOfPlayers;
    private int points;
}

@Getter
public class Game {
    private int groupCount;
    private ArrayList<Clan> clans;
}
