package cc.moecraft.icq.sender.returndata.returnpojo.get;

import cc.moecraft.icq.sender.returndata.returnpojo.ReturnPojoBase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * The class {@code RFile} is the return pojo class for the api nodes
 * that returns a file. For example, {@code /get_record} and
 * {@code /get_image}.
 * <p>
 * Class created by the HyDEV Team on 2019-03-29!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-29 19:26
 */
@Data
@Setter(AccessLevel.NONE)
public class RFile implements ReturnPojoBase
{
    @SerializedName("file")
    @Expose
    private String file;
}
