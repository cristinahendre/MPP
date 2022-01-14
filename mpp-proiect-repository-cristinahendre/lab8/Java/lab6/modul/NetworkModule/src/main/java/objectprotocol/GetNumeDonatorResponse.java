package objectprotocol;


import Domain.Donator;

public class GetNumeDonatorResponse implements ResponseObject {

    private Iterable<Donator> donatori;

    public Iterable<Donator> getDonatori() {
        return donatori;
    }

    public GetNumeDonatorResponse(Iterable<Donator> donatori) {
        this.donatori = donatori;
    }
}
