package exscript.core;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.*;
import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import cc.moecraft.icq.user.Group;
import cc.moecraft.icq.user.GroupUser;
import cc.moecraft.icq.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExCommand {

	protected final ExEngine engine;
	protected final Class<? extends IcqCommand> type;
	protected final CommandProperties commandProperties;
	protected final String functionName;

	public ExCommand(ExEngine engine, Class<? extends IcqCommand> type, String commandName, String functionName) {
		this(engine, type, CommandProperties.name(commandName), functionName);
	}

	public ExCommand(ExEngine engine, Class<? extends IcqCommand> type, String[] commandName, String functionName) {
		this(engine, type, Arrays.asList(commandName), functionName);
	}

	public ExCommand(ExEngine engine, Class<? extends IcqCommand> type, List<String> commandName, String functionName) {
		this(engine, type, new CommandProperties(commandName.remove(0), new ArrayList<>(commandName)), functionName);
	}

	public ExCommand(ExEngine engine, Class<? extends IcqCommand> type, CommandProperties commandProperties, String functionName) {
		this.engine = engine;
		this.type = type;
		this.commandProperties = commandProperties;
		this.functionName = functionName;
	}

	public IcqCommand toIcqCommand() {
		if (EverywhereCommand.class.equals(type)) {
			return toEverywhereCommand();
		} else if (PrivateCommand.class.equals(type)) {
			return toPrivateCommand();
		} else if (GroupCommand.class.equals(type)) {
			return toGroupCommand();
		} else if (DiscussCommand.class.equals(type)) {
			return toDiscussCommand();
		}
		throw new UnsupportedOperationException();
	}

	public EverywhereCommand toEverywhereCommand() {
		return new EverywhereCommand() {
			@Override
			public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
				return engine.invokeFunctionCatched(functionName, event, sender, command, args).toString();
			}

			@Override
			public CommandProperties properties() {
				return commandProperties;
			}
		};
	}

	public PrivateCommand toPrivateCommand() {
		return new PrivateCommand() {
			@Override
			public String privateMessage(EventPrivateMessage event, User sender, String command, ArrayList<String> args) {
				return engine.invokeFunctionCatched(functionName, event, sender, command, args).toString();
			}

			@Override
			public CommandProperties properties() {
				return commandProperties;
			}
		};
	}

	public GroupCommand toGroupCommand() {
		return new GroupCommand() {
			@Override
			public String groupMessage(EventGroupMessage event, GroupUser sender, Group group, String command, ArrayList<String> args) {
				return engine.invokeFunctionCatched(functionName, event, sender, group, command, args).toString();
			}

			@Override
			public CommandProperties properties() {
				return commandProperties;
			}
		};
	}

	public DiscussCommand toDiscussCommand() {
		return new DiscussCommand() {
			@Override
			public String discussMessage(EventDiscussMessage event, GroupUser sender, Group discuss, String command, ArrayList<String> args) {
				return engine.invokeFunctionCatched(functionName, event, sender, discuss, command, args).toString();
			}

			@Override
			public CommandProperties properties() {
				return commandProperties;
			}
		};
	}

}
