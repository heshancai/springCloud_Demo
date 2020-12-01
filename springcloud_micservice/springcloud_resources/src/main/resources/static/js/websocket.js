/**
 * 初始化websocket
 * 与服务器进行长连接
 * 心跳处理机制
 */
//初始化变量
var ws;//websocket对象
var heartTime=2000;//心跳时间
var closeTime=5000;//连接关闭的时间
var reconnTime=5000;//重连时间
function initWs(callBack) { //callBack设置回调的方法 得到方法自定义参数

    //如果有自定义的心跳时间,使用自定义的心跳时间
    if(callBack.heartTime){
        heartTime = callBack.heartTime;
    }

    if(callBack.closeTime){
        closeTime = callBack.closeTime;
    }

    if(callBack.reconnTime){
        reconnTime = callBack.reconnTime;
    }


    //判断浏览器是否支持Websocket服务
    if (window.WebSocket) {

        //websocket对象连接服务器
        //使用参数的url连接服务器
        ws = new WebSocket(callBack.url);

        /**
         * 设置服务器连接成功的回调方法
         */
        ws.onopen = function () {
            console.log("与服务器建立连接成功");

            //开始发送心跳
            hear();

            //定时关闭服务器
            closeconn();

            //连接服务器成功 向服务器注册当前用户的消息
            //调用自定义的open方法
            if(callBack.myopen){
                callBack.myopen();
            }
        };

        /**
         * 服务器断开连接的回调方法
         */
        ws.onclose = function () {
            console.log("与服务器断开连接");

            //开始重连
            setTimeout(function () {
                reconn(callBack);
            },reconnTime);

            //关闭心跳 心跳不为空
            if(heartTimeOut){
                //清除定时器
                clearTimeout(heartTimeOut);
                heartTimeOut=null;
            }

            //服务器断开接收消息的方法
            if(callBack.myclose){
                callBack.myclose();
            }
        };

        /**
         * 与服务器连接异常的方法
         */
        ws.onerror = function () {
            console.log("服务器连接异常。");
            //调用自定义的方法
            if(callBack.myerror){
                callBack.myerror();
            }
        };

        /**
         * 收到服务器回复消息的回调方法
         * @param msg
         */
        ws.onmessage = function (msg) {
            console.log("接收到服务器回复的消息" + msg.data);
            //转为json字符串  -->js 对象
            var msgObject=JSON.parse(msg.data);
            //服务器回复的心跳消息
            if(msgObject.type==2){
                console.log("接收到服务器心跳的消息");
                //清除定时器
                clearTimeout(closeTimeOut);
                //开启关闭连接的定时器
                closeconn();
            }else {
                //非心跳回复 ，其他消息
                if(callBack.mymessage){
                    //处理非心跳消息
                    callBack.mymessage(msgObject);
                }
            }
        };

    } else {
        alert("浏览器太垃圾了，请去换一台电脑");
    }
}

/**
 * 重新连接服务器的方法
 * @param callBack
 */
function reconn(callBack) {
    console.log("开始重连服务器。。");
    initWs(callBack);
}




/**
 * 发送心跳的方法
 */
var heartTimeOut=null;
function hear() {
    console.log("发送一次心跳");
    //构造发送心跳的消息 js json对象
    var heartMsg = {"type": 2};
    //发送心跳的消息
    sendMsgObj(heartMsg);

    //两秒 发送一次心跳
    heartTimeOut=setTimeout(function () {
        //递归调用
        hear();
    }, heartTime);
}

/**
 * 关闭与服务器连接的方法
 */
var closeTimeOut;
function closeconn() {
    closeTimeOut=setTimeout(function () {
        //如果websocket不为空
        if(ws){
            ws.close();
        }
    },closeTime);
}



/**
 * 发送心跳信息到服务器
 * 信息类型:json对象
 */
function sendMsgObj(msg) {
    //json对象转为json字符串
    var msgStr=JSON.stringify(msg);
    //发送消息
    sendMsg(msgStr);
}


/**
 * 发送字符串到服务器的方法
 * @param msg
 */
function sendMsg(msg) {
    //如果websocket不为空
    if(ws){
        ws.send(msg);
    }else{
        alert("服务器连接异常,发送失败");
    }
}

