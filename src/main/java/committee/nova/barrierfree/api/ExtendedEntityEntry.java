package committee.nova.barrierfree.api;

public interface ExtendedEntityEntry {
    boolean barrierfree$canClimb();

    void barrierfree$markCanClimb(boolean canClimb);

    boolean barrierfree$canOpenDoor();

    void barrierfree$markCanOpenDoor(boolean canOpenDoor);
}
