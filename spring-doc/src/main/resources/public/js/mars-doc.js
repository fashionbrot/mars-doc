let util={
    post:function (url,data,isStream,async,callback){
        util.submit(url,"post","json",data,async,isStream,callback);
    },get:function (url,async,callback){
        util.submit(url,"get","json","",async,false,callback);
    },submit:function (url,type,dataType,data,async,isStream,callback){
        var config = {
            url: url,
            type: type,
            dataType: dataType,
            data: data,
            async:async,
            beforeSend: function () {
                util.openLoading();
            },
            success: function(result) {
                util.closeLoading();
                // if (typeof callback == "function") {
                //     callback(result);
                // }
                util.successCallback(result,callback);
            },error:function () {
                util.closeLoading();
            }
        };
        if (isStream){
            var newConfig={
                contentType: "application/json",
                data: JSON.stringify(data)
            }
            config = $.extend(config, newConfig);
        }
        $.ajax(config)
    },openLoading:function (){
        // loading();
    },closeLoading(){
        // loaded();
    },isSuccess:function (result){
        if (result && result.code!= this.getSuccessCode()){
            util.alert(result.msg);
        }
    },getSuccessCode:function (){
        return 0;
    },alert:function (msg,duration){
        alert(msg)
    },successCallback:function (result,callback){
        if (result){
            if (typeof callback == "function") {
                callback(result);
            }
        }
        if (result && result.code &&  result.code== util.getSuccessCode()){
            if (typeof callback == "function") {
                callback(result);
            }
        }else{
            if (result && result.msg){
                util.alert(result.msg);
            }else{
                // util.alert("处理失败，请联系管理员");
            }
        }
    }
}

// 本地缓存处理
let storage = {
    set: function(key, value) {
        window.localStorage.setItem(key, value);
    },
    get: function(key) {
        return window.localStorage.getItem(key);
    },
    remove: function(key) {
        window.localStorage.removeItem(key);
    },
    clear: function() {
        window.localStorage.clear();
    }
};

// 日志打印封装处理
let log = {
    log: function(msg) {
        console.log(msg);
    },
    info: function(msg) {
        console.info(msg);
    },
    warn: function(msg) {
        console.warn(msg);
    },
    error: function(msg) {
        console.error(msg);
    }
};

