package cc.moecraft.test.fools.commands;

import cc.moecraft.icq.command.CommandProperties;

/**
 * TODO: No description yet...
 * <p>
 * Class created by the HyDEV Team on 2019-03-31!
 *
 * @author HyDEV Team (https://github.com/HyDevelop)
 * @author Hykilpikonna (https://github.com/hykilpikonna)
 * @author Vanilla (https://github.com/VergeDX)
 * @since 2019-03-31 22:27
 */
public class CommandRecent extends AFCommand
{
    @Override
    public CommandProperties properties()
    {
        return new CommandProperties("recent", "最近成绩", "ercent");
    }
}
