print("Running on "+version);

var evt_all = function(evt) {
    // print(evt);
}

var msg_all = function(evt) {
    print("收到信息："+evt.getMessage());
}

var friend_request = function(evt) {
    print("收到了"+evt.getUserId()+"的好友请求。接受！");
    evt.accept();
}

var type_event_local_send_message = Java.type("cc.moecraft.icq.event.events.local.EventLocalSendMessage");
var local_send = function(evt) {
    if(evt instanceof type_event_local_send_message) {
        print("这确实是一个EventLocalSendMessage事件！");
    }
    else {
        // 这不是一个EventLocalSendMessage事件！
        // 但是因为注册的是Event，就不推荐有过多操作。
    }
}

var API = ExScript.api();

var command_ban = function(evt, sender, group, command, args) {
    if(args.size() > 0) {
        API.setGroupKick(group.getId(), args.get(0), false);
        return("成了。");
    }
    else {
        return("QQ号呢？");
    }
}

var command_speak = function(evt, sender, group, command, args) {
    if(args.size() > 0) {
        var msg = args.get(0);
    }
}

/* 函数注册 */
print("Loading test.js!");
ExScript.addEvent("Event", "evt_all");
ExScript.addEvent("EventMessage", "msg_all");
ExScript.addEvent("EventFriendRequest", "friend_request");
ExScript.addEvent("Event", "local_send");
ExScript.addCommand("GroupCommand", "ban", "command_ban");