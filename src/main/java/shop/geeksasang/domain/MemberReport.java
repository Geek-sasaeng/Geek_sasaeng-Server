package shop.geeksasang.domain;

import javax.persistence.*;

@Entity
@DiscriminatorValue("member")
public class MemberReport extends Report{

}
