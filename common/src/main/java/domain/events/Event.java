package domain.events;

public interface Event {
    String getAggregateId();
    int getAggregateVersion();
}
