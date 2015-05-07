package org.announcer;

public class AnEvent {
    public final String payload;

    public AnEvent(String payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnEvent anEvent = (AnEvent) o;
        if (!payload.equals(anEvent.payload)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return payload.hashCode();
    }
}
