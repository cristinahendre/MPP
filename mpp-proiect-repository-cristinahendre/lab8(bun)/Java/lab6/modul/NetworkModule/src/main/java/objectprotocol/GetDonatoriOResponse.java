package objectprotocol;


import Domain.CazCaritabil;
import Domain.Donator;

public class GetDonatoriOResponse implements ResponseObject {



    Iterable<Donator> donators;

    public GetDonatoriOResponse(Iterable<Donator> cazuri) {
        this.donators = cazuri;
    }

    public Iterable<Donator> getDonatori() {
        return donators;
    }
}
