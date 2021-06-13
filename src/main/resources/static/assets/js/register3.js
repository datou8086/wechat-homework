function checkUserName() {    //验证用户名
    var fname = document.myform.username.value;
    var reg = /^[0-9a-zA-Z]/;
    if (fname.length != 0) {
        for (i = 0; i < fname.length; i++) {
            if (!reg.test(fname)) {
                alert("只能输入字母或数字");
                return false;
            }
        }
        if (fname.length < 4 || fname.length > 16) {
            alert("只能输入4-16个字符");
            return false;
        }
    }
    else {
        alert("请输入用户名");
        document.myform.username.focus();
        return false;
    }
    return true;
}

function passCheck() { //验证密码
    var userpass = document.myform.password.value;
    if (userpass == "") {
        alert("未输入密码 \n" + "请输入密码");
        document.myform.password.focus();
        return false;
    }
    if (userpass.length < 6 || userpass.length > 12) {
        alert("密码必须在 6-12 个字符。\n");
        return false;
    }
    return true;
}

function passCheck2() {
    var p1 = document.myform.password.value;
    var p2 = document.myform.password2.value;
    if (p1 != p2) {
        alert("确认密码与密码输入不一致");
        return false;
    } else {
        return true;
    }
}

function checkEmail() {
    var Email = document.getElementById("email").value;
    var e = Email.indexOf("@" && ".");
    if (Email.length != 0) {
        if (e > 0) {
            if (Email.charAt(0) == "@" && ".") {
                alert("符号@和符号.不能再邮件地址第一位");
                return false;
            }
            else {
                return true;
            }
        }
        else {
            alert("电子邮件格式不正确\n" + "必须包含@符号和.符号！");
            return false;
        }
    }
    else {
        alert("请输入电子邮件！");
        return false;
    }
}

function checkbirthday() {    //验证用户名
    var year = document.myform.birthday.value;
    if (year < 1949 || year > 2007) {
        alert("年份范围从1949-2007年");
        return false;
    }
    return true;
}

function validateform() {
    if (checkUserName() && passCheck() && passCheck2() && checkEmail() && checkbirthday())
        return true;
    else
        return false;
}

function clearText() {
    document.myform.user.value = "";
    document.myform.password.value = "";
}

//显示隐藏对应的密码方法:
function show_hide_pwd(id)
{
    let type = $("#"+id).attr('type')
    if (type === "password") {
        $("#"+id+"eye").attr('src', "assets/img/eye_close.svg");
        $("#"+id).attr("type", "text");
    } else {
        $("#"+id+"eye").attr('src', "assets/img/eye_open.svg");
        $("#"+id).attr("type", "password");
    }
}

function encryptAndRegister() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var code = document.getElementById("code").value;
    //调用后端接口获取公钥
    $.ajax({
        type:'POST',
        url:'api/account/getPublicKey',
        data:{},
        success:function(result){
            // 解析公钥
            var publicKey = result.data;
            //对密码进行加密
            var encrypt = new JSEncrypt();
            encrypt.setPublicKey(publicKey);
            var encryptedPasswd = encrypt.encrypt(password);

            // 用公钥加密后的密码传输给后端
            register(username, encryptedPasswd, code);
        }
    });
}

function register(username, encryptedPasswd, code) {
    $.ajax({
        type: "POST",
        dataType: "json",
        url: 'api/account/register',
        contentType: "application/json",
        data:JSON.stringify({
            "loginType": 'username',
            "loginId": username,
            "password": encryptedPasswd,
            "verificationCode": code
        }),
        success: function (result) {
            if (result.code == 200) {
                alert("注册成功，点击确定跳转我的页面");
                setTokenToCookie(result.data.token);
                window.location.href = "../user.html";
            }else {
                alert(result.message)
            }
        }
    });
}

function setTokenToCookie(value) {
    var Days = 1; //此 cookie 将被保存 30 天
    var exp = new Date();
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = "jwt-token=" + escape(value) + ";expires=" + exp.toGMTString();
}

function changeCodeImg() {
    document.getElementById("captcha_code").src = "/api/code/genImageCode?r=" + Math.random();
}