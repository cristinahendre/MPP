package Repo;

import Domain.Participant;

public interface IParticipantRepository extends Repository<Integer, Participant> {

    int getIdParticipant(String nume);
}
