package com.puffpiratestudios.algos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class TierCalc extends Calc{

    public int numTiers;

    public TierCalc() {
        super();
        numTiers = 2;
    }

    public double calculateTotal() {
        int totalDays = 0;
        double energyPerCycle;

        setGregorianCalendars();

        if (numSeasons == 1) {
            energyPerCycle = dailyEnergyUsage() * numDaysBetweenPeriod(startBC, endBC);
            return seasons.get(0).calcTotal(energyPerCycle, 1);
        }

        ArrayList<Integer> daysInSeasons = calcNumDaysPerSeason();
        double totalCost = 0;
        for (int i : daysInSeasons) {
            totalDays += i;
        }

        energyPerCycle = dailyEnergyUsage() * totalDays;

        for (int i = 0; i < numSeasons; i++) {
            double pctOfBC = (double) daysInSeasons.get(i) / totalDays;
            totalCost += (pctOfBC * seasons.get(i).calcTotal(energyPerCycle, 1));
        }

        return totalCost;
    }
}
