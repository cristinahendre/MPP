package Domain.Validators;

import Domain.Donator;

public class DonatorValidator implements Validator<Donator> {

    /**
     * Valideaza un donator
     * @param entity ->donatorul
     * @throws ValidationException
     */
    @Override
    public void validate(Donator entity) throws ValidationException {
        if(entity.getNume().equals(" ") || entity.getNume().equals(""))
            throw new ValidationException("Numele este vid.");
        if(entity.getPrenume().equals(" ") || entity.getPrenume().equals(""))
            throw new ValidationException("Prenumele este vid.");
        if(entity.getAdresa().equals(" ") || entity.getAdresa().equals(""))
            throw new ValidationException("Adresa este vida.");
        if(entity.getNrTelefon() < 0)
            throw new ValidationException("Numarul de telefon e negativ.");


    }
}
