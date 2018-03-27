<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>作业类型管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript" src="/static/jquery/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/static/jctl/jcAjaxUpload.js"></script>
    <script type="text/javascript" src="/static/layer/layer.js"></script>
    <script>
        $(function () {
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
                    $('#icon').attr('value', data.url);
                }
            });
        })
    </script>

    <script src="/static/jctl/img/jcImgUpload.js"></script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/jobtype/jobType/">作业类型列表</a></li>
    <li class="active"><a href="${ctx}/jobtype/jobType/form?id=${jobType.id}">作业类型<shiro:hasPermission
            name="jobtype:jobType:edit">${not empty jobType.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="jobtype:jobType:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="jobType" action="${ctx}/jobtype/jobType/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">名称：</label>
        <div class="controls">
            <form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">


        <label class="control-label">图标：</label>
        <div class="controls">
            <img style="width: 200px;height: 200px" src="${jobType.icon}" id="viewImg">
            <input type="button" class="btn btn-primary" id="uploadPhoto" value="修改">
            <input  name="icon"  id="icon" type="hidden" value="${jobType.icon}">
        </div>
    </div>
    <div class="form-actions">
            <%--<shiro:hasPermission name="jobtype:jobType:edit">--%>
        <input id="btnSubmit" class="btn btn-primary" type="submit"
               value="保 存"/>&nbsp;
            <%--</shiro:hasPermission>--%>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>