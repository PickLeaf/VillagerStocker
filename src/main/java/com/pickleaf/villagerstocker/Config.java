package com.pickleaf.villagerstocker;

public class Config {
    private static double stockerWorkRadius = Double.MIN_VALUE;

    public static double getStockerWorkRadius() {
        return stockerWorkRadius;
    }

    public static void setStockerWorkRadius(double stockerWorkRadius) {
        if(Config.stockerWorkRadius != Double.MIN_VALUE) return;
        Config.stockerWorkRadius = stockerWorkRadius;
    }

    public static float getStockerConsumeChance() {
        return stockerConsumeChance;
    }

    public static void setStockerConsumeChance(float stockerConsumeChance) {
        if(Config.stockerConsumeChance != Float.MIN_VALUE) return;
        Config.stockerConsumeChance = stockerConsumeChance;
    }

    private static float stockerConsumeChance = Float.MIN_VALUE;
}
