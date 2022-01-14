package objectprotocol;


import Domain.CazCaritabil;

public class GetCazuriResponse implements ResponseObject {

    Iterable<CazCaritabil> cazuri;

    public GetCazuriResponse(Iterable<CazCaritabil> cazuri) {
        this.cazuri = cazuri;
    }

    public Iterable<CazCaritabil> getCazuri() {
        return cazuri;
    }
}
