package objectprotocol;


import dto.DonatorDTO;

public class GetNumeDonatorRequest implements RequestObject {

    private DonatorDTO dto;

    public GetNumeDonatorRequest(DonatorDTO dto) {
        this.dto = dto;
    }

    public DonatorDTO getDonator() {
        return dto;
    }
}
