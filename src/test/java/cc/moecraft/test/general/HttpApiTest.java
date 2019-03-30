package cc.moecraft.test.general;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;
import cc.moecraft.icq.sender.IcqHttpApi;
import cc.moecraft.icq.sender.message.components.ComponentImage;
import cc.moecraft.icq.sender.returndata.RawReturnData;
import cc.moecraft.icq.sender.returndata.ReturnData;
import cc.moecraft.icq.sender.returndata.ReturnListData;
import cc.moecraft.icq.sender.returndata.ReturnStatus;
import cc.moecraft.icq.sender.returndata.returnpojo.get.RStatus;
import cc.moecraft.logger.HyLogger;
import cc.moecraft.test.icq.listeners.ExceptionListener;
import cc.moecraft.test.icq.listeners.TestListener;
import cn.hutool.core.thread.ThreadUtil;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * The class {@code HttpApiTest} is the unit tester for the http api.
 * <p>
 * Class created by the HyDEV Team on 2019-03-21!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-21 21:05
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HttpApiTest
{
    private static final long TEST_RECEIVE_QQ = 233186409; // 用来测试的QQ, 必须不是机器人QQ, 而且要在群里x
    private static final long TEST_RECEIVE_GR = 491046707; // 用来测试的群

    private static PicqBotX bot;
    private static IcqHttpApi api;
    private static HyLogger logger;

    @BeforeClass
    public static void init()
    {
        // 创建机器人对象 ( 接收端口 )
        bot = new PicqBotX(new PicqConfig(31092)
                .setDebug(true)
                .setSecret("This is secret")
                .setAccessToken("Brq4KSm+3UdaUJnLZ+AJfj**v-vePWL$")
                //.setUseAsync(true)
                //.setApiRateLimited(true)
        );

        // 添加一个机器人账户 ( 名字, 发送URL, 发送端口 )
        bot.addAccount("Bot01", "127.0.0.1", 31091);

        // 注册事件
        bot.getEventManager().registerListeners(new TestListener(), new ExceptionListener());

        // 启用指令管理器, Unit test
        bot.enableCommandManager("ut -");

        api = bot.getAccountManager().getNonAccountSpecifiedApi();
        logger = bot.getLogger();

        // 启动机器人
        new Thread(bot::startBot).start();
        ThreadUtil.safeSleep(2000);
    }

    /**
     * Test if a return data is not failed.
     *
     * @param data Return data
     */
    private void test(ReturnData data)
    {
        test(data.getStatus(), data.getReturnCode(), data);
    }

    /**
     * Test if a return list data is not failed.
     *
     * @param data Return list data
     */
    private void test(ReturnListData data)
    {
        test(data.getStatus(), data.getReturnCode(), data);
    }

    /**
     * Test if a raw return data is not failed.
     *
     * @param data Raw return data
     */
    private void test(RawReturnData data)
    {
        test(data.getStatus(), data.getReturnCode(), data);
    }

    /**
     * Test return data
     *
     * @param status Status
     * @param code Return Code
     * @param data Return Data
     */
    private void test(ReturnStatus status, Long code, Object data)
    {
        try
        {
            assert status != ReturnStatus.failed;
            assert code == 0 || code == 1;
        }
        catch (AssertionError e)
        {
            logger.error("Error: " + data);
            throw e;
        }

        logger.log(data.toString());
    }

    @Test
    public void testGetStatus()
    {
        RStatus status = api.getStatus().getData();
        logger.debug(status.toString());

        assert status.getGood();
    }

    @Test
    public void testGetStrangerInfo()
    {
        test(api.getStrangerInfo(TEST_RECEIVE_QQ));
    }

    @Test
    public void testGetVIPInfo()
    {
        test(api.getVIPInfo(TEST_RECEIVE_QQ));
    }

    @Test
    public void testGetGroupList()
    {
        test(api.getGroupList());
    }

    @Test
    public void testGetGroupMemberInfo()
    {
        test(api.getGroupMemberInfo(TEST_RECEIVE_GR, TEST_RECEIVE_QQ));
    }

    @Test
    public void testGetGroupInfo()
    {
        test(api.getGroupInfo(TEST_RECEIVE_GR));
    }

    @Test
    public void testGetGroupMemberList()
    {
        test(api.getGroupMemberList(TEST_RECEIVE_GR));
    }

    @Test
    public void testGetFriendList()
    {
        test(api.getFriendList());
    }

    @Test
    public void testGetVersionInfo()
    {
        test(api.getVersionInfo());
    }

    @Test
    @Ignore // 忽略, 因为需要手动获取一个图片ID
    public void testGetImage()
    {
        test(api.getImage("E779A15BE07A796AAA0A6124F071DA36.png"));
    }

    @Test
    public void testPM()
    {
        test(api.sendPrivateMsg(TEST_RECEIVE_QQ, "Unit test - testPM()"));
    }

    @Test
    public void testPMAutoEscape()
    {
        test(api.sendPrivateMsg(TEST_RECEIVE_QQ, "Unit test - testPMAutoEscape()", true));
    }

    @Test
    public void testGM()
    {
        test(api.sendGroupMsg(TEST_RECEIVE_GR, "Unit test - testGM()"));
    }

    @Test
    public void testGMAutoEscape()
    {
        test(api.sendGroupMsg(TEST_RECEIVE_GR, "Unit test - testGMAutoEscape()", true));
    }

    @Test
    public void testSendImage()
    {
        test(api.sendGroupMsg(TEST_RECEIVE_GR, new ComponentImage("https://i.imgur.com/noTnhSM.png").toString()));
    }

    // TODO: Test DM

    @Test
    public void testDeleteMsg()
    {
        // Doesn't work async, because message id can't be obtained.
        if (bot.getConfig().isApiAsync()) return;

        long toDelete = api.sendGroupMsg(TEST_RECEIVE_GR, "Unit test - testDeleteMsg()").getData().getMessageId();
        ThreadUtil.safeSleep(2000);
        test(api.deleteMsg(toDelete));
    }

    @Test
    public void testSendLike()
    {
        test(api.sendLike(TEST_RECEIVE_QQ, 2));
    }

    @Test
    public void testSetGroupKick()
    {
        // 没有人可以踢哇 (╯‵□′)╯︵┻━┻
    }

    @Test
    public void testGroupBan()
    {
        test(api.setGroupBan(TEST_RECEIVE_GR, TEST_RECEIVE_QQ, 1));
    }

    @Test
    public void testGroupWholeBan()
    {
        test(api.setGroupWholeBan(TEST_RECEIVE_GR, true));
        test(api.setGroupWholeBan(TEST_RECEIVE_GR, false));
    }

    @Test
    public void testSetGroupAdmin()
    {
        test(api.setGroupAdmin(TEST_RECEIVE_GR, TEST_RECEIVE_QQ, true));
        test(api.setGroupAdmin(TEST_RECEIVE_GR, TEST_RECEIVE_QQ, false));
    }

    @Test
    public void testSetGroupAnonymous()
    {
        test(api.setGroupAnonymous(TEST_RECEIVE_GR, false));
        test(api.setGroupAnonymous(TEST_RECEIVE_GR, true));
    }

    @Test
    public void testSetGroupCard()
    {
        test(api.setGroupCard(TEST_RECEIVE_GR, TEST_RECEIVE_QQ, "UT-GC"));
    }

    // TODO: setGroupLeave

    @Test
    public void testSetGroupSpecialTitle()
    {
        test(api.setGroupSpecialTitle(TEST_RECEIVE_GR, TEST_RECEIVE_QQ, "UT-GC", 200));
    }

    // Manual test: setFriendAndRequest, setGroupAndRequest, approveGroupRequest, setRestart

    @Test
    public void testCleanDataDir()
    {
        test(api.cleanDataDir("image"));
        test(api.cleanDataDir("record"));
        test(api.cleanDataDir("show"));
        test(api.cleanDataDir("bface"));
    }

    // Manual test: cleanPluginLog
    // 不能清Log哇 (╯‵□′)╯︵┻━┻

}
