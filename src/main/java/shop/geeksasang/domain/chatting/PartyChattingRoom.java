package shop.geeksasang.domain.chatting;

public class PartyChattingRoom extends ChattingRoom{

    private String title;

    //TODO:채팅과 일대다 연관관계 테스트 중
//    @DBRef
//    private List<Chatting> chattings;
//    @DocumentReference
//    private List<Chatting> chattings;

    public PartyChattingRoom(String title) {
        super();
        this.title = title;
    }

//    public PartyChattingRoom(String title, List<Chatting> chattings) {
//        this.title = title;
//        this.chattings = chattings;
//    }

    @Override
    public String toString() {
        return "DeliveryPartyChattingRoom{" +
                "id='" + id + '\'' +
                ", baseEntity=" + baseEntityMongo +
                ", title='" + title + '\'' +
                '}';
    }



}