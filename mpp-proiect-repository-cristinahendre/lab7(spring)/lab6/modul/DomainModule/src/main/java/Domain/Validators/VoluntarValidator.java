package Domain.Validators;

import Domain.Voluntar;

public class VoluntarValidator implements  Validator<Voluntar> {

    /**
     * Valideaza un voluntar
     * @param entity ->voluntarul
     * @throws ValidationException
     */
    @Override
    public void validate(Voluntar entity) throws ValidationException {
        if(entity.getEmail().equals("") || entity.getEmail().equals(" "))
            throw new ValidationException("Email-ul este vid.");

        if(entity.getParola().equals("") || entity.getParola().equals(" "))
            throw  new ValidationException("Parola este vida.");


    }
}
