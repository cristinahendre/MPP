package objectprotocol;

import dto.VoluntarDTO;


public class LoginRequestObject implements RequestObject {
    private VoluntarDTO user;

    public LoginRequestObject(VoluntarDTO user) {
        this.user = user;
    }

    public VoluntarDTO getUser() {
        return user;
    }
}
