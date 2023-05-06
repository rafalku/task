package com.ing.task;

import java.util.*;

public class GameCalculator {
    private final static int MAX_GROUP_COUNT = 1000;
    private final Game game;

    GameCalculator(Game game) {
        this.game = game;
    }

    public List<List<Clan>> calculate() {
        List<List<Clan>> gamePlan = new LinkedList<>();
        final int groupCount = game.getGroupCount();
        ArrayList<Clan> clans = game.getClans();

        if (clans == null) {
            return gamePlan;
        }

        clans.sort((c1, c2) -> {
            if (c1.getPoints() == c2.getPoints()) {
                return Integer.compare(c1.getNumberOfPlayers(), c2.getNumberOfPlayers());
            }
            return Integer.compare(c2.getPoints(), c1.getPoints());
        });

        int[] groupCounts = new int[MAX_GROUP_COUNT];
        for (Clan clan : clans) {
            groupCounts[clan.getNumberOfPlayers() - 1]++;
        }

        while (clans.size() > 0) {
            int currentGroupCount = 0;
            List<Clan> group = new LinkedList<>();
            for (Iterator<Clan> iterator = clans.iterator(); iterator.hasNext(); ) {
                Clan clan = iterator.next();
                int numberOfPlayers = clan.getNumberOfPlayers();
                if (currentGroupCount + numberOfPlayers <= groupCount) {
                    group.add(clan);
                    currentGroupCount += numberOfPlayers;
                    iterator.remove();
                    groupCounts[numberOfPlayers - 1]--;
                }
                if (currentGroupCount == groupCount) {
                    break;
                }
                final int availableSpots = groupCount - currentGroupCount;
                boolean keepSearching = false;
                for (int i = 1; i <= availableSpots; i++) {
                    if (groupCounts[i - 1] > 0) {
                        keepSearching = true;
                        break;
                    }
                }
                if (!keepSearching) {
                    break;
                }
            }
            gamePlan.add(group);
        }

        return gamePlan;
    }
}
