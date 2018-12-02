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

        return new AstroLocation(split[0], Double.valueOf(split[1]), Double.valueOf(split[2]), Double.valueOf(split[3]), Float.valueOf(split[4]), Float.valueOf(split[5]));
    }
}
