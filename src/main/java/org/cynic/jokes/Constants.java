package org.cynic.jokes;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public final class Constants {

    private Constants() {
    }

    public static final Marker AUDIT_MARKER = MarkerFactory.getMarker("AUDIT");
    public static final Integer FEIGN_INTERNAL_ERROR = -1;
}
