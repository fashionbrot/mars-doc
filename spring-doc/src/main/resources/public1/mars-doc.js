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

let common = {
    formatValue:function (value){
        if (value=="null" || value==null){
            return ""
        }else{
            return value;
        }
    },
    // 判断字符串是否为空
    isEmpty: function (value) {
        if (typeof value =='object'){
            for(let key in value){
                return false;
            }
            return true;
        }else if (typeof value=='string'){
            if (value == null || this.trim(value) == "") {
                return true;
            }
        }
        return false;
    },
    // 判断一个字符串是否为非空串
    isNotEmpty: function (value) {
        return !common.isEmpty(value);
    },
    trim: function (value) {
        if (value == null) {
            return "";
        }
        return value.toString().replace(/(^\s*)|(\s*$)|\r|\n/g, "");
    },
    // 比较两个字符串（大小写敏感）
    equals: function (str, that) {
        return str == that;
    },
    // 比较两个字符串（大小写不敏感）
    equalsIgnoreCase: function (str, that) {
        return String(str).toUpperCase() === String(that).toUpperCase();
    },
    // 判断字符串是否是以start开头
    startWith: function(value, start) {
        var reg = new RegExp("^" + start);
        return reg.test(value)
    },
    // 判断字符串是否是以end结尾
    endWith: function(value, end) {
        var reg = new RegExp(end + "$");
        return reg.test(value)
    },
    join: function(array, separator) {
        if (common.isEmpty(array)) {
            return null;
        }
        return array.join(separator);
    },
    like:function (str,keyWord){
        var reg = new RegExp(keyWord);
        //如果字符串中不包含目标字符会返回-1
        if(str.match(reg)){
            //匹配成功do something
            return true;
        }
        return false;
    }, guid: function()  {
        function S4() {
            return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
        }
        return (S4()+S4()+S4()+S4()+S4()+S4()+S4()+S4());
    }, getCharset : function() {
        let charSet = "";
        if (isIe()){
            charSet = document.charset;
        }else if (isFireFox()){
            charSet = document.characterSet;
        }else{
            charSet = document.characterSet;
        }
        return charSet;

        //判断是否IE
        function isIe() {
            let i = navigator.userAgent.toLowerCase().indexOf("msie");
            return i >= 0;
        }
        //判断是否firefox
        function isFireFox() {
            let i = navigator.userAgent.toLowerCase().indexOf("firefox");
            return i >= 0;
        }
    },getByteLength : function(text) {
        let len = 0;
        if (common.isNotEmpty(text)){
            for (let i=0; i<text.length; i++) {
                if (text.charCodeAt(i)>127 || text.charCodeAt(i)==94) {
                    len += 2;
                } else {
                    len ++;
                }
            }
        }
        return len;
    },getTextSpace: function (text){
        if (common.isEmpty(text)){
            text="";
        }
        let limit = unescape(encodeURIComponent(text)).length;

        let size = "";
        if(limit < 0.1 * 1024){                            //小于0.1KB，则转化成B
            size = limit.toFixed(2) + "B"
        }else if(limit < 0.1 * 1024 * 1024){            //小于0.1MB，则转化成KB
            size = (limit/1024).toFixed(2) + "KB"
        }else if(limit < 0.1 * 1024 * 1024 * 1024){        //小于0.1GB，则转化成MB
            size = (limit/(1024 * 1024)).toFixed(2) + "MB"
        }else{                                            //其他转化成GB
            size = (limit/(1024 * 1024 * 1024)).toFixed(2) + "GB"
        }
        let sizeStr = size + "";                        //转成字符串
        let index = sizeStr.indexOf(".");                    //获取小数点处的索引
        let dou = sizeStr.substr(index + 1 ,2)            //获取小数点后两位的值
        if(dou == "00"){                                //判断后两位是否为00，如果是则删除00
            return sizeStr.substring(0, index) + sizeStr.substr(index + 3, 2)
        }
        return size;
    }
}

