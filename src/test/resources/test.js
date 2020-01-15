var evt_all = function(evt) {
    print(evt);
}

var msg_all = function(evt) {
    print("收到信息："+evt.getMessage());
    evt.respond("Great!");
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

/* 函数注册 */
print("Loading test.js!");
var reg = Java.type("taskeren.extrabot.jshorn.JavaScriptManager");
reg.addFunction("Event", "evt_all");
reg.addFunction("EventMessage", "msg_all");
reg.addFunction("EventFriendRequest", "friend_request");
reg.addFunction("Event", "local_send");