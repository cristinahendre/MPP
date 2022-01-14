package Domain.Validators;

import Domain.Donatie;

public class DonatieValidator implements  Validator<Donatie> {

    /**
     * Valideaza o donatie
     * @param entity -> donatia
     * @throws ValidationException
     */
    @Override
    public void validate(Donatie entity) throws ValidationException {
        if(entity.getSuma_donata() < 0)
            throw new ValidationException("Ati donat o suma negativa.");

        if(entity.getCaz() == null)
            throw new ValidationException("Introduceti un caz");

        if(entity.getDonator() == null)
            throw new ValidationException("Donatorul nu este introdus");
    }
}
