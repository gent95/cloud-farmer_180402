<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>修改头像</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript" src="/static/jquery/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/static/jctl/jcAjaxUpload.js"></script>
    <script type="text/javascript" src="/static/layer/layer.js"></script>
    <script>
        $(function () {
            $("#updatePhoto").click(function () {
                var id = $("#userId").val();
                var photo = $("#photo").val();
                $.ajax({
                    url: "${ctx}/sys/user/updateUserPhoto?id="+id+"&photo="+photo,
                    type: "POST",
                    dataType:'json',
                    success: function (data) {
                        if(data.flag == '1'){
                            layer.msg("修改成功！");
                         /*  var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                            parent.layer.close(index);*/
                        }else {
                            layer.msg("修改失败！")
                        }

                    }
                });
            });


            new AjaxUpload('#uploadPhoto', {
                action: '/admin/uploadFile',
                name: 'file',
                responseType: 'json',
                onSubmit: function (file, ext) {
                    if (ext && /^(jpg|png|bmp)$/.test(ext.toLowerCase())) {
                        this.setData({
                            'file': file
                        });
                    } else {
                        layer.msg("请上传格式为 jpg|png|bmp 的图片！");
                        return false;
                    }
                },
                onComplete: function (file, data) {
                    if (data.flag == '0') {
                        layer.msg(data.msg);
                        return;
                    }
                    $('#viewImg').attr('src', data.url);
                    $('#photo').attr('value', data.url);
                }
            });
        })
    </script>
</head>
<body style="height: 100%; text-align: center">
<%--<button id="uploadPhoto">选择图片</button>--%>
<br>
<div>
    <img style="border-radius: 50%;width: 200px;height: 200px" src="${user.photo}" id="viewImg">
    <input type="button" class="btn btn-primary" id="uploadPhoto" value="修改">
</div>

<form action="${ctx}/sys/user/info" method="post">
    <input id="photo" type="hidden" name="photo" value="${user.photo}">
    <input id="userId" type="hidden" name="id" value="${user.id}">
    <input type="button" id="updatePhoto" class="btn btn-primary" value="确定">
    <input type="button" class="btn btn-primary" value="取消">
</form>
</body>
</html>