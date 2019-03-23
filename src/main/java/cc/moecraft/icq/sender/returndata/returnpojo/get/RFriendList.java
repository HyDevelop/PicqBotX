package cc.moecraft.icq.sender.returndata.returnpojo.get;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.util.List;

/**
 * 此类由 Hykilpikonna 在 2018/05/25 创建!
 * Created by Hykilpikonna on 2018/05/25!
 * Github: https://github.com/hykilpikonna
 * QQ: admin@moecraft.cc -OR- 871674895
 *
 * @author Hykilpikonna
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.NONE)
public class RFriendList extends ReturnPojoBase
{
    @SerializedName("friend_group_id")
    @Expose
    public Long friendGroupId;

    @SerializedName("friend_group_name")
    @Expose
    public String friendGroupName;

    @SerializedName("friends")
    @Expose
    public List<Friend> friends = null;

    @Data
    public class Friend
    {
        @SerializedName("nickname")
        @Expose
        public String nickname;

        @SerializedName("remark")
        @Expose
        public String remark;

        @SerializedName("user_id")
        @Expose
        public Long userId;
    }
}
