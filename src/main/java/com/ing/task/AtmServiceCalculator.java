package com.ing.task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class AtmServiceCalculator {

    private static final int NUMBER_OF_REQUESTS_TYPES = AtmRequestType.values().length;
    private static final int MAX_NUMBER_OF_REGIONS = 9999;
    private static final int MAX_NUMBER_OF_ATMS = 9999;

    private static class RegionalOrder {
        List<LinkedList<Integer>> prioritizedAtms = new ArrayList<>(NUMBER_OF_REQUESTS_TYPES);

        public RegionalOrder() {
            for (int i = 0; i < NUMBER_OF_REQUESTS_TYPES; i++) {
                prioritizedAtms.add(new LinkedList<>());
            }
        }
    }

    private final AtmServiceOrder[] orders;

    public AtmServiceCalculator(AtmServiceOrder[] orders) {
        this.orders = orders;
    }

    public String calculate() {
        RegionalOrder[] regionalOrders = new RegionalOrder[MAX_NUMBER_OF_REGIONS];
        for (AtmServiceOrder order : orders) {
            int region = order.getRegion() - 1;
            int priority = order.getRequestType().getPriority();
            int atmId = order.getAtmId();
            if (regionalOrders[region] == null) {
                regionalOrders[region] = new RegionalOrder();
            }
            regionalOrders[region].prioritizedAtms.get(priority).add(atmId);
        }

        StringBuilder output = new StringBuilder("[");
        for (int i = 0; i < regionalOrders.length; i++) {
            if (regionalOrders[i] != null) {
                String s = String.format("{\"region\":%d,\"atmId\":%%d},", i + 1);
                boolean[] scheduledAtms = new boolean[MAX_NUMBER_OF_ATMS];
                for (int j = 0; j < NUMBER_OF_REQUESTS_TYPES; j++) {
                    for (int atmId : regionalOrders[i].prioritizedAtms.get(j)) {
                        if (!scheduledAtms[atmId - 1]) {
                            output.append(String.format(s, atmId));
                            scheduledAtms[atmId - 1] = true;
                        }
                    }
                }
            }
        }
        if (output.charAt(output.length() - 1) == ',') {
            output.deleteCharAt(output.length() - 1);
        }
        output.append("]");
        return output.toString();
    }

}
