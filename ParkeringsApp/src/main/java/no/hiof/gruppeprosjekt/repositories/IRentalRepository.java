package no.hiof.gruppeprosjekt.repositories;

public interface IRentalRepository {
    void createRentalAgreement(int rentalId, int userId, int spaceId, int duration);
}
