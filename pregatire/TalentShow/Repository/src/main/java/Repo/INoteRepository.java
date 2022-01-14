package Repo;

import Domain.Nota;
import Domain.Participant;
import Domain.Persoana;

import java.util.List;

public interface INoteRepository extends Repository<Integer, Nota>{


    int votare(Nota n, Persoana p , int nota);

    List<Participant> getParticipantiDelaJuriu(String juriu);

    Nota getPunctajeParticipant(String nume);
}
