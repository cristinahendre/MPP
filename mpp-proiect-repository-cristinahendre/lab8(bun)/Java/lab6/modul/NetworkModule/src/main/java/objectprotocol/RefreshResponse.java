package objectprotocol;

import Domain.Donatie;

public class RefreshResponse implements UpdateResponseObject{
    public RefreshResponse(Donatie dto) {
        this.dto = dto;
    }

    public Donatie getDto() {
        return dto;
    }

    Donatie dto;
}
