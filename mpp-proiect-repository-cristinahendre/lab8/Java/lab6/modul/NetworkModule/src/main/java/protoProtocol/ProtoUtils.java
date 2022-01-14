package protoProtocol;

import Domain.CazCaritabil;
import Domain.Donatie;
import Domain.Donator;
import Domain.Voluntar;
import dto.DonatorDTO;
import dto.VoluntarDTO;

import java.util.ArrayList;
import java.util.List;


public class ProtoUtils {
    public static protoProtocol.ChatProtobufs.ChatRequest createLoginRequest(VoluntarDTO user){
        ChatProtobufs.Voluntar userDTO= ChatProtobufs.Voluntar.newBuilder().setEmail(user.getEmail()).setPasswd(user.getPasswd()).build();
        protoProtocol.ChatProtobufs.ChatRequest request= protoProtocol.ChatProtobufs.ChatRequest.newBuilder().setType(protoProtocol.ChatProtobufs.ChatRequest.Type.Login)
               .addVoluntar(userDTO).build();
        return request;
    }


    public static protoProtocol.ChatProtobufs.ChatRequest GetNumeDonRequest(DonatorDTO user){
        ChatProtobufs.Donator userDTO= ChatProtobufs.Donator.newBuilder()
                .setNume(user.getNume()).build();
        protoProtocol.ChatProtobufs.ChatRequest request= protoProtocol.ChatProtobufs.ChatRequest
                .newBuilder().setType(ChatProtobufs.ChatRequest.Type.SearchDonator)
                .setDonator(userDTO).build();
        return request;
    }



    public static protoProtocol.ChatProtobufs.ChatRequest SaveDonatieRequest(Donatie d){
        ChatProtobufs.Donatie dto= ChatProtobufs.Donatie.newBuilder()
                .setCaz(ChatProtobufs.CazCaritabil.newBuilder().
                        setId(d.getCaz().getId()).setNume(d.getCaz().getNume())
                        .setSuma(d.getCaz().getSuma_donata()))
                        .setDon(ChatProtobufs.Donator.newBuilder().
                                setId(d.getDonator().getId()).setNume(d.getDonator().getNume())
                                .setPrenume(d.getDonator().getPrenume())
                                .setAdresa(d.getDonator().getAdresa()).setNr(d.getDonator().getNrTelefon())).
                        setSuma(d.getSuma_donata()).build();
        protoProtocol.ChatProtobufs.ChatRequest request= protoProtocol.ChatProtobufs.ChatRequest
                .newBuilder().setType(ChatProtobufs.ChatRequest.Type.SaveDonatie)
                .setDonatie(dto).build();
        return request;
    }


    public static protoProtocol.ChatProtobufs.ChatRequest SaveDonatorRequest(DonatorDTO d){
        ChatProtobufs.Donator dto= ChatProtobufs.Donator.newBuilder()
                .setNume(d.getNume()).setPrenume(d.getPrenume())
                .setAdresa(d.getAdresa()).setNr(d.getTel()).build();

        protoProtocol.ChatProtobufs.ChatRequest request= protoProtocol.ChatProtobufs.ChatRequest
                .newBuilder().setType(ChatProtobufs.ChatRequest.Type.SaveDonator)
                .setDonator(dto).build();
        return request;
    }


    public  static  Donatie getDonatie(ChatProtobufs.ChatRequest request){
        ChatProtobufs.Donatie d =request.getDonatie();
        ChatProtobufs.CazCaritabil caz=d.getCaz();
        CazCaritabil caz1=new CazCaritabil(caz.getNume());
        caz1.setSuma_donata(caz.getSuma());
        caz1.setId(caz.getId());

        ChatProtobufs.Donator donator =d.getDon();
        Donator don1=new Donator(donator.getNume(), donator.getPrenume(), donator.getAdresa(),
                (long) donator.getNr());
        don1.setId(donator.getId());
        Donatie don =new Donatie(don1,caz1,d.getSuma());
        don.setId(d.getId());

        return don;
    }

    public static Donator getDonatorfromDTO(ChatProtobufs.ChatRequest request){
        ChatProtobufs.Donator don=request.getDonator();
        Donator nou =new Donator(don.getNume(),don.getPrenume(),don.getAdresa(),
                (long) don.getNr());
        return nou;
    }

    public  static  Donatie getDonatie(ChatProtobufs.ChatResponse request){
        ChatProtobufs.Donatie d =request.getDonatie();
        ChatProtobufs.CazCaritabil caz=d.getCaz();
        CazCaritabil caz1=new CazCaritabil(caz.getNume());
        caz1.setSuma_donata(caz.getSuma());
        caz1.setId(caz.getId());

        ChatProtobufs.Donator donator =d.getDon();
        Donator don1=new Donator(donator.getNume(), donator.getPrenume(), donator.getAdresa(),
                (long) donator.getNr());
        don1.setId(donator.getId());
        Donatie don =new Donatie(don1,caz1,d.getSuma());
        don.setId(d.getId());

        return don;
    }




    public static protoProtocol.ChatProtobufs.ChatRequest GetDateDonRequest(DonatorDTO user){
        ChatProtobufs.Donator userDTO= ChatProtobufs.Donator.newBuilder()
                .setNume(user.getNume()).setPrenume(user.getPrenume())
                .setAdresa(user.getAdresa()).setNr(user.getTel()).build();
        protoProtocol.ChatProtobufs.ChatRequest request= protoProtocol.ChatProtobufs.ChatRequest
                .newBuilder().setType(ChatProtobufs.ChatRequest.Type.GetDateDonator)
                .setDonator(userDTO).build();
        return request;
    }


    public static protoProtocol.ChatProtobufs.ChatRequest createLogoutRequest(Voluntar user){
        ChatProtobufs.Voluntar userDTO= ChatProtobufs.Voluntar.newBuilder()
                .setId(user.getId()).setNume(user.getNume())
                .setPrenume(user.getPrenume()).setEmail(user.getEmail())
                .setPasswd(user.getParola()).build();
        protoProtocol.ChatProtobufs.ChatRequest request= protoProtocol.ChatProtobufs
                .ChatRequest.newBuilder().setType(protoProtocol.ChatProtobufs.ChatRequest
                        .Type.Logout).addVoluntar(userDTO).build();

        return request;

    }

    public static protoProtocol.ChatProtobufs.ChatRequest createSendMesssageRequest(Voluntar message){
//        protoProtocol.ChatProtobufs.Message messageDTO= protoProtocol.ChatProtobufs.Message.newBuilder().
//                setSenderId(message.getSender().getId())
//                .setReceiverId(message.getReceiver().getId())
//                .setText(message.getText()).build();
//        protoProtocol.ChatProtobufs.ChatRequest request= protoProtocol.ChatProtobufs.ChatRequest.newBuilder()
//                .setType(protoProtocol.ChatProtobufs.ChatRequest.Type.SendMessage)
//                .setMessage(messageDTO).build();
//        return request;
        return null;
    }

    public static protoProtocol.ChatProtobufs.ChatRequest createLoggedFriendsRequest(Voluntar user){
//        protoProtocol.ChatProtobufs.User userDTO= protoProtocol.ChatProtobufs.User.newBuilder().setId(user.getId()).build();
//        protoProtocol.ChatProtobufs.ChatRequest request= protoProtocol.ChatProtobufs.ChatRequest.newBuilder()
//                .setType(protoProtocol.ChatProtobufs.ChatRequest.Type.GetLoggedFriends)
//                .setUser(userDTO).build();
//        return request;
        return null;
    }


    public static protoProtocol.ChatProtobufs.ChatResponse createOkResponse(Voluntar vol){
        protoProtocol.ChatProtobufs.ChatResponse response= ChatProtobufs.ChatResponse.newBuilder()
                .addVoluntar(0, ChatProtobufs.Voluntar.newBuilder().setId(vol.getId()).setNume(vol.getNume())
                .setEmail(vol.getEmail()).setPrenume(vol.getPrenume()).setPasswd(vol.getParola()))
                        .setType(ChatProtobufs.ChatResponse.Type.Ok).build();
        return response;
    }


    public static protoProtocol.ChatProtobufs.ChatResponse getDateDonResponse(Donator vol){
        protoProtocol.ChatProtobufs.ChatResponse response= ChatProtobufs.ChatResponse.newBuilder()
               .addDonator(0,ChatProtobufs.Donator.newBuilder().setId(vol.getId()).setNume(vol.getNume())
                       .setPrenume(vol.getPrenume()).setAdresa(vol.getAdresa())
                       .setNr(vol.getNrTelefon()))
                .setType(ChatProtobufs.ChatResponse.Type.GetDonator).build();
        return response;
    }



    public static ChatProtobufs.ChatResponse createLogoutResponse(){
        ChatProtobufs.ChatResponse response=ChatProtobufs.ChatResponse.newBuilder()
                .setType(ChatProtobufs.ChatResponse.Type.Logout).build();
        return response;
    }

    public static ChatProtobufs.ChatResponse createRefreshResponse(Donatie d){
        ChatProtobufs.Donatie userDTO= ChatProtobufs.Donatie.newBuilder()
                .setCaz(ChatProtobufs.CazCaritabil.newBuilder().
                        setId(d.getCaz().getId()).setNume(d.getCaz().getNume())
                        .setSuma(d.getCaz().getSuma_donata()))
                .setDon(ChatProtobufs.Donator.newBuilder().
                        setId(d.getDonator().getId()).setNume(d.getDonator().getNume())
                        .setPrenume(d.getDonator().getPrenume())
                        .setAdresa(d.getDonator().getAdresa()).setNr(d.getDonator().getNrTelefon())).
                        setSuma(d.getSuma_donata()).build();

        ChatProtobufs.ChatResponse response=ChatProtobufs.ChatResponse.newBuilder()
                .setType(ChatProtobufs.ChatResponse.Type.Refresh)
                .setDonatie(userDTO).build();
        return response;
    }


    public static ChatProtobufs.ChatResponse createSaveDonatieResponse(){
        ChatProtobufs.ChatResponse response=ChatProtobufs.ChatResponse.newBuilder()
                .setType(ChatProtobufs.ChatResponse.Type.NewDonatie).build();
        return response;
    }

    public static ChatProtobufs.ChatResponse createSaveDonatorResponse(){
        ChatProtobufs.ChatResponse response=ChatProtobufs.ChatResponse.newBuilder()
                .setType(ChatProtobufs.ChatResponse.Type.NewDonator).build();
        return response;
    }

    public static protoProtocol.ChatProtobufs.ChatResponse createErrorResponse(String text){
        protoProtocol.ChatProtobufs.ChatResponse response= protoProtocol.ChatProtobufs.ChatResponse.newBuilder()
                .setType(protoProtocol.ChatProtobufs.ChatResponse.Type.Error)
                .setError(text).build();
        return response;
    }

    public static protoProtocol.ChatProtobufs.ChatResponse createFriendLoggedInResponse(Voluntar user){
//        protoProtocol.ChatProtobufs.User userDTO= protoProtocol.ChatProtobufs.User.newBuilder().setId(user.getId()).build();
//
//        protoProtocol.ChatProtobufs.ChatResponse response=protoProtocol.ChatProtobufs.ChatResponse.newBuilder()
//                .setType(protoProtocol.ChatProtobufs.ChatResponse.Type.FriendLoggedIn)
//                .setUser(userDTO).build();
//        return response;
        return null;
    }




    public static String getError(protoProtocol.ChatProtobufs.ChatResponse response){
        String errorMessage=response.getError();
        return errorMessage;
    }

    public static VoluntarDTO getVoluntar(ChatProtobufs.ChatRequest request){
        ChatProtobufs.Voluntar vol =request.getVoluntar(0);
        VoluntarDTO dto=new VoluntarDTO(vol.getEmail(), vol.getPasswd());

        return dto;
    }

    public static Voluntar getVoluntarBun(ChatProtobufs.ChatRequest request){
        ChatProtobufs.Voluntar vol =request.getVoluntar(0);
        Voluntar v=new Voluntar(vol.getNume(),vol.getPrenume(),vol.getEmail(),
                vol.getPasswd());
        v.setId(vol.getId());

        return v;
    }



    public static DonatorDTO getDonator(ChatProtobufs.ChatRequest request){
        ChatProtobufs.Donator vol =request.getDonator();
        DonatorDTO dto=new DonatorDTO(vol.getNume());

        return dto;
    }

    public static DonatorDTO getDTODonator(ChatProtobufs.ChatRequest request){
        ChatProtobufs.Donator vol =request.getDonator();
        DonatorDTO dto=new DonatorDTO(vol.getNume(),vol.getPrenume(),vol.getAdresa(),
                (long) vol.getNr());

        return dto;
    }


    public static ChatProtobufs.ChatRequest createGetCazuriRequest() {
        protoProtocol.ChatProtobufs.ChatRequest request= protoProtocol
                .ChatProtobufs.ChatRequest.newBuilder().setType(ChatProtobufs
                        .ChatRequest.Type.GetCazuri)
               .build();
        return request;
    }

    public static ChatProtobufs.ChatResponse createNullDonatorResp() {
        ChatProtobufs.ChatResponse request= ChatProtobufs.ChatResponse
                .newBuilder().setType(ChatProtobufs
                        .ChatResponse.Type.Unknown)

                .build();
        return request;
    }

    public static ChatProtobufs.ChatRequest createGetDonatoriAll() {
        protoProtocol.ChatProtobufs.ChatRequest request= protoProtocol.ChatProtobufs.ChatRequest.newBuilder()
                .setType(ChatProtobufs.ChatRequest.Type.GetDonatori)
                .build();
        return request;
    }



    public static protoProtocol.ChatProtobufs.ChatResponse GetCazuriResponse(Iterable<CazCaritabil> cazuri){
       List<ChatProtobufs.CazCaritabil> nou=new ArrayList<>();
        for(CazCaritabil c:cazuri){
            ChatProtobufs.CazCaritabil caz=ChatProtobufs.CazCaritabil.newBuilder().
                    setId(c.getId()).setNume(c.getNume()).setSuma(c.getSuma_donata()).build();
            nou.add(caz);
        }
        ChatProtobufs.ChatResponse.Builder response=ChatProtobufs.ChatResponse.newBuilder()
                .setType(ChatProtobufs.ChatResponse.Type.GetCazuri)
                .addAllCazuri(nou);


        return response.build();
    }



    public static protoProtocol.ChatProtobufs.ChatResponse GetDonatoriResponse(Iterable<Donator> donators){
        List<ChatProtobufs.Donator> nou=new ArrayList<>();
        for(Donator c:donators){
            ChatProtobufs.Donator caz=ChatProtobufs.Donator.newBuilder().
                    setId(c.getId()).setNume(c.getNume()).setPrenume(c.getPrenume())
                    .setAdresa(c.getAdresa()).setNr(c.getNrTelefon())
                    .build();
            nou.add(caz);
        }
        ChatProtobufs.ChatResponse.Builder response=ChatProtobufs.ChatResponse.newBuilder()
                .setType(ChatProtobufs.ChatResponse.Type.GetDonatori)
                .addAllDonator(nou);


        return response.build();
    }



    public static protoProtocol.ChatProtobufs.ChatResponse GetNumeDonResponse(Iterable<Donator> donators){
        List<ChatProtobufs.Donator> nou=new ArrayList<>();
        for(Donator c:donators){
            ChatProtobufs.Donator caz=ChatProtobufs.Donator.newBuilder().
                    setId(c.getId()).setNume(c.getNume()).setPrenume(c.getPrenume())
                    .setAdresa(c.getAdresa()).setNr(c.getNrTelefon())
                    .build();
            nou.add(caz);
        }
        ChatProtobufs.ChatResponse.Builder response=ChatProtobufs.ChatResponse.newBuilder()
                .setType(ChatProtobufs.ChatResponse.Type.GetNumeDon)
                .addAllDonator(nou);


        return response.build();
    }



    public static Iterable<CazCaritabil> getCazuriProto(ChatProtobufs.ChatResponse response){

        List<CazCaritabil> cazs=new ArrayList<>();
        int i=0;
        while(i!=response.getCazuriCount()){
            ChatProtobufs.CazCaritabil userDTO=response.getCazuri(i);
            CazCaritabil user=new CazCaritabil(userDTO.getNume());
            user.setSuma_donata(userDTO.getSuma());
            user.setId(userDTO.getId());
            cazs.add(user);
            i++;
        }
        return cazs;
    }


    public static Iterable<Donator> getDonatoriProto(ChatProtobufs.ChatResponse response){

        List<Donator> cazs=new ArrayList<>();
        int i=0;
        while(i!=response.getDonatorCount()){
            ChatProtobufs.Donator userDTO=response.getDonator(i);
            Donator d=new Donator(userDTO.getNume(), userDTO.getPrenume(), userDTO.getAdresa(),
                    (long) userDTO.getNr());
            d.setId(userDTO.getId());
            cazs.add(d);
            i++;
        }
        return cazs;
    }
}
