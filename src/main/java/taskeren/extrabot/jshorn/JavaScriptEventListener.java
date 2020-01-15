package taskeren.extrabot.jshorn;

import cc.moecraft.icq.event.Event;
import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.*;
import cc.moecraft.icq.event.events.notice.EventNotice;
import cc.moecraft.icq.event.events.notice.EventNoticeFriendAdd;
import cc.moecraft.icq.event.events.notice.EventNoticeGroupBan;
import cc.moecraft.icq.event.events.notice.EventNoticeGroupUpload;
import cc.moecraft.icq.event.events.notice.groupadmin.EventNoticeGroupAdminChange;
import cc.moecraft.icq.event.events.notice.groupadmin.EventNoticeGroupAdminRemove;
import cc.moecraft.icq.event.events.notice.groupadmin.EventNoticeGroupAdminSet;
import cc.moecraft.icq.event.events.notice.groupmember.EventNoticeGroupMemberChange;
import cc.moecraft.icq.event.events.notice.groupmember.decrease.EventNoticeGroupMemberDecrease;
import cc.moecraft.icq.event.events.notice.groupmember.decrease.EventNoticeGroupMemberKick;
import cc.moecraft.icq.event.events.notice.groupmember.decrease.EventNoticeGroupMemberKickBot;
import cc.moecraft.icq.event.events.notice.groupmember.decrease.EventNoticeGroupMemberLeave;
import cc.moecraft.icq.event.events.notice.groupmember.increase.EventNoticeGroupMemberApprove;
import cc.moecraft.icq.event.events.notice.groupmember.increase.EventNoticeGroupMemberIncrease;
import cc.moecraft.icq.event.events.notice.groupmember.increase.EventNoticeGroupMemberInvite;
import cc.moecraft.icq.event.events.request.EventFriendRequest;
import cc.moecraft.icq.event.events.request.EventGroupAddRequest;
import cc.moecraft.icq.event.events.request.EventGroupInviteRequest;
import cc.moecraft.icq.event.events.request.EventRequest;

public class JavaScriptEventListener extends IcqListener {

	protected final JavaScriptManager mgr;

	public JavaScriptEventListener(JavaScriptManager mgr) {
		this.mgr = mgr;
	}

	// ALL

	@EventHandler
	public void all(Event evt) {
		mgr.callFunction(Event.class, evt);
	}

	// Message Package

	@EventHandler
	public void message(EventMessage evt) {
		mgr.callFunction(EventMessage.class, evt);
	}

	@EventHandler
	public void messagePrivate(EventPrivateMessage evt) {
		mgr.callFunction(EventPrivateMessage.class, evt);
	}

	@EventHandler
	public void messageGroup(EventGroupMessage evt) {
		mgr.callFunction(EventGroupMessage.class, evt);
	}

	@EventHandler
	public void messageDiscuss(EventDiscussMessage evt) {
		mgr.callFunction(EventDiscussMessage.class, evt);
	}

	@EventHandler
	public void messageGroupOrDiscuss(EventGroupOrDiscussMessage evt) {
		mgr.callFunction(EventGroupOrDiscussMessage.class, evt);
	}

	// Notice Package

	@EventHandler
	public void notice(EventNotice evt) {
		mgr.callFunction(EventNotice.class, evt);
	}

	@EventHandler
	public void noticeFriendAdd(EventNoticeFriendAdd evt) {
		mgr.callFunction(EventNoticeFriendAdd.class, evt);
	}

	@EventHandler
	public void noticeGroupBan(EventNoticeGroupBan evt) {
		mgr.callFunction(EventNoticeGroupBan.class, evt);
	}

	@EventHandler
	public void noticeGroupUpload(EventNoticeGroupUpload evt) {
		mgr.callFunction(EventNoticeGroupUpload.class, evt);
	}

	@EventHandler
	public void noticeGroupAdminChange(EventNoticeGroupAdminChange evt) {
		mgr.callFunction(EventNoticeGroupAdminChange.class, evt);
	}

	@EventHandler
	public void noticeGroupAdminRemove(EventNoticeGroupAdminRemove evt) {
		mgr.callFunction(EventNoticeGroupAdminRemove.class, evt);
	}

	@EventHandler
	public void noticeGroupAdminSet(EventNoticeGroupAdminSet evt) {
		mgr.callFunction(EventNoticeGroupAdminSet.class, evt);
	}

	@EventHandler
	public void noticeGroupMemberChange(EventNoticeGroupMemberChange evt) {
		mgr.callFunction(EventNoticeGroupMemberChange.class, evt);
	}

	@EventHandler
	public void noticeGroupMemberDecrease(EventNoticeGroupMemberDecrease evt) {
		mgr.callFunction(EventNoticeGroupMemberDecrease.class, evt);
	}

	@EventHandler
	public void noticeGroupMemberKick(EventNoticeGroupMemberKick evt) {
		mgr.callFunction(EventNoticeGroupMemberKick.class, evt);
	}

	@EventHandler
	public void noticeGroupMemberKickBot(EventNoticeGroupMemberKickBot evt) {
		mgr.callFunction(EventNoticeGroupMemberKickBot.class, evt);
	}

	@EventHandler
	public void noticeGroupMemberLeave(EventNoticeGroupMemberLeave evt) {
		mgr.callFunction(EventNoticeGroupMemberLeave.class, evt);
	}

	@EventHandler
	public void noticeGroupMemberApprove(EventNoticeGroupMemberApprove evt) {
		mgr.callFunction(EventNoticeGroupMemberApprove.class, evt);
	}

	@EventHandler
	public void noticeGroupMemberIncrease(EventNoticeGroupMemberIncrease evt) {
		mgr.callFunction(EventNoticeGroupMemberIncrease.class, evt);
	}

	@EventHandler
	public void noticeGroupMemberInvite(EventNoticeGroupMemberInvite evt) {
		mgr.callFunction(EventNoticeGroupMemberInvite.class, evt);
	}

	// Request Package

	@EventHandler
	public void friendRequest(EventFriendRequest evt) {
		mgr.callFunction(EventFriendRequest.class, evt);
	}

	@EventHandler
	public void groupAddRequest(EventGroupAddRequest evt) {
		mgr.callFunction(EventGroupAddRequest.class, evt);
	}

	@EventHandler
	public void groupInviteRequest(EventGroupInviteRequest evt) {
		mgr.callFunction(EventGroupInviteRequest.class, evt);
	}

	@EventHandler
	public void request(EventRequest evt) {
		mgr.callFunction(EventRequest.class, evt);
	}

}
