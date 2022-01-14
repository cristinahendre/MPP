package objectprotocol;

import dto.DonatorDTO;

public class GetDateDonRequest  implements RequestObject{

    private DonatorDTO dto;

    public GetDateDonRequest(DonatorDTO dto) {
        this.dto = dto;
    }

    public DonatorDTO getDonator() {
        return dto;
    }
}
