package cc.moecraft.test.general;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.sender.IcqHttpApi;
import cc.moecraft.logger.HyLogger;
import cc.moecraft.test.icq.listeners.ExceptionListener;
import cc.moecraft.test.icq.listeners.TestListener;
import cn.hutool.core.thread.ThreadUtil;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
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
}
