package com.narwhalcompany.cantrip.model.main;

import java.util.Date;

public class ItineraryItem {
    private int tripId;
    private Date startDate;
    private Date endDate;
    private int planId;

    public ItineraryItem(int tripId, Date startDate, Date endDate) {
        this.tripId = tripId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
