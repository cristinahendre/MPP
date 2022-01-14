package objectprotocol;

import Domain.Donator;

public class GetDateDonResponse implements ResponseObject{
    private Donator don;

    public Donator getDon() {
        return don;
    }

    public GetDateDonResponse(Donator don) {
        this.don = don;
    }
}
