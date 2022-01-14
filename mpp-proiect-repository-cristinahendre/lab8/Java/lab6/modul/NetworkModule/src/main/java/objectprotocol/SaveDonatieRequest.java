package objectprotocol;


import Domain.Donatie;

public class SaveDonatieRequest implements  RequestObject{

    Donatie d;

    public SaveDonatieRequest(Donatie d) {
        this.d = d;
    }

    public Donatie getDonatie() {
        return d;
    }
}
