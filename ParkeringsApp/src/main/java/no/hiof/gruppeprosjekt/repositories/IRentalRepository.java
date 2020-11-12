package no.hiof.gruppeprosjekt.repositories;

public interface IRentalRepository {
    void createRentalAgreement(int userId, int spaceId, int duration);
}
