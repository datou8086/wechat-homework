function logout() {
    if (confirm('请确认是否退出登录')){
        $.ajax({
            type: "POST",
            dataType: "json",
            url: 'api/account/logout',
            contentType: "application/json",
            data:JSON.stringify({
            }),
            success: function (result) {
                if (result.code == 200) {
                    alert("退出登录成功，点击确定跳转首页");
                    clearTokenToCookie();
                    window.location.href = "../index.html";
                }else {
                    alert(result.message)
                }
            }
        });
    }
}

function clearTokenToCookie() {
    var Days = 1; //此 cookie 将被保存 30 天
    var exp = new Date();
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = "jwt-token=0;expires=" + exp.toGMTString();
}
