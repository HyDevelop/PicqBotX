package exscript;

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

public class ExScriptListener extends IcqListener {

	protected final ExScript es;

	public ExScriptListener(ExScript es) {
		this.es = es;
	}

	// ALL

	@EventHandler
	public void all(Event evt) {
		es.callFunction(Event.class, evt);
	}

	// Message Package

	@EventHandler
	public void message(EventMessage evt) {
		es.callFunction(EventMessage.class, evt);
	}

	@EventHandler
	public void messagePrivate(EventPrivateMessage evt) {
		es.callFunction(EventPrivateMessage.class, evt);
	}

	@EventHandler
	public void messageGroup(EventGroupMessage evt) {
		es.callFunction(EventGroupMessage.class, evt);
	}

	@EventHandler
	public void messageDiscuss(EventDiscussMessage evt) {
		es.callFunction(EventDiscussMessage.class, evt);
	}

	@EventHandler
	public void messageGroupOrDiscuss(EventGroupOrDiscussMessage evt) {
		es.callFunction(EventGroupOrDiscussMessage.class, evt);
	}

	// Notice Package

	@EventHandler
	public void notice(EventNotice evt) {
		es.callFunction(EventNotice.class, evt);
	}

	@EventHandler
	public void noticeFriendAdd(EventNoticeFriendAdd evt) {
		es.callFunction(EventNoticeFriendAdd.class, evt);
	}

	@EventHandler
	public void noticeGroupBan(EventNoticeGroupBan evt) {
		es.callFunction(EventNoticeGroupBan.class, evt);
	}

	@EventHandler
	public void noticeGroupUpload(EventNoticeGroupUpload evt) {
		es.callFunction(EventNoticeGroupUpload.class, evt);
	}

	@EventHandler
	public void noticeGroupAdminChange(EventNoticeGroupAdminChange evt) {
		es.callFunction(EventNoticeGroupAdminChange.class, evt);
	}

	@EventHandler
	public void noticeGroupAdminRemove(EventNoticeGroupAdminRemove evt) {
		es.callFunction(EventNoticeGroupAdminRemove.class, evt);
	}

	@EventHandler
	public void noticeGroupAdminSet(EventNoticeGroupAdminSet evt) {
		es.callFunction(EventNoticeGroupAdminSet.class, evt);
	}

	@EventHandler
	public void noticeGroupMemberChange(EventNoticeGroupMemberChange evt) {
		es.callFunction(EventNoticeGroupMemberChange.class, evt);
	}

	@EventHandler
	public void noticeGroupMemberDecrease(EventNoticeGroupMemberDecrease evt) {
		es.callFunction(EventNoticeGroupMemberDecrease.class, evt);
	}

	@EventHandler
	public void noticeGroupMemberKick(EventNoticeGroupMemberKick evt) {
		es.callFunction(EventNoticeGroupMemberKick.class, evt);
	}

	@EventHandler
	public void noticeGroupMemberKickBot(EventNoticeGroupMemberKickBot evt) {
		es.callFunction(EventNoticeGroupMemberKickBot.class, evt);
	}

	@EventHandler
	public void noticeGroupMemberLeave(EventNoticeGroupMemberLeave evt) {
		es.callFunction(EventNoticeGroupMemberLeave.class, evt);
	}

	@EventHandler
	public void noticeGroupMemberApprove(EventNoticeGroupMemberApprove evt) {
		es.callFunction(EventNoticeGroupMemberApprove.class, evt);
	}

	@EventHandler
	public void noticeGroupMemberIncrease(EventNoticeGroupMemberIncrease evt) {
		es.callFunction(EventNoticeGroupMemberIncrease.class, evt);
	}

	@EventHandler
	public void noticeGroupMemberInvite(EventNoticeGroupMemberInvite evt) {
		es.callFunction(EventNoticeGroupMemberInvite.class, evt);
	}

	// Request Package

	@EventHandler
	public void friendRequest(EventFriendRequest evt) {
		es.callFunction(EventFriendRequest.class, evt);
	}

	@EventHandler
	public void groupAddRequest(EventGroupAddRequest evt) {
		es.callFunction(EventGroupAddRequest.class, evt);
	}

	@EventHandler
	public void groupInviteRequest(EventGroupInviteRequest evt) {
		es.callFunction(EventGroupInviteRequest.class, evt);
	}

	@EventHandler
	public void request(EventRequest evt) {
		es.callFunction(EventRequest.class, evt);
	}

}
