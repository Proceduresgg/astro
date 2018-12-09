package me.procedures.astro.utils;

import me.procedures.astro.location.AstroLocation;

public class LocationUtil {

    public static String serializeAstroLocation(AstroLocation location) {

        return location.getWorld() + "|" + location.getX() + "|" +
                location.getY() + "|" +
                location.getZ() + "|" +
                location.getYaw() + "|" +
                location.getPitch();
    }

    public static AstroLocation deserializeAstroLocation(String serializedLocation) {
        String[] split = serializedLocation.split("|");

        return new AstroLocation(split[0], Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5]));
    }
}
