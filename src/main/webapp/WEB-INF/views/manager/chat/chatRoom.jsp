<%--
  Created by IntelliJ IDEA.
  User: gent
  Date: 2017/4/19
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>智农聊天室</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="alternate icon" type="image/png" href="${pageContext.request.contextPath}/static/assets/i/favicon.png"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/assets/css/amazeui.min.css"/>
    <link rel="stylesheet" href="${ctxStatic}/css/table-response.css"/>
    <script src="${pageContext.request.contextPath}/static/assets/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/assets/js/amazeui.min.js"></script>
    <!-- UM相关资源 -->
    <link href="${pageContext.request.contextPath}/static/assets/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/static/assets/umeditor/umeditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/static/assets/umeditor/umeditor.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/assets/umeditor/lang/zh-cn/zh-cn.js"></script>
    <style>
        html,body,.am-topbar{background: none;}
        .am-comment-hd{background: none; border: none;}
        .am-comment-meta time{float: right;}
        .am-topbar{color: #fff; border: none;}
        .am-comment-main{
            border-radius: 10px;
            font: 14px PingFangSC-Regular,"Microsoft Yahei", "Helvetica Neue",Arial,,Helvetica,sans-serif,Lato;
            color: #fff;
        }
        .am-comment-warning.am-comment-flip .am-comment-main:after{border-left-color: transparent;}
        .am-comment-bd{padding: 0 15px 10px;}
        .edui-container{width: 100%;}
    </style>
    <script>
        /**
         * 编辑器resize
         */
        $(document).ready(function(){
            $(window).resize(function(){
                $('.edui-container, .edui-body-container').css('width', '100%')
            })
        })
    </script>
</head>
<body>
    <header class="am-topbar am-topbar-fixed-top">
        <div class="am-container">
            <h1 class="am-topbar-brand">智农-咨询室</h1>
        </div>
    </header>
    <div id="main">
        <!-- 聊天内容展示区域 -->
        <div id="ChatBox" class="am-g am-g-fixed" style="width: 100%;">
            <div class="am-u-lg-12">
                <ul id="chatContent" class="am-comments-list am-comments-list-flip">
                    <li id="msgtmp" class="am-comment" style="display:none;">
                        <a href="">
                            <img class="am-comment-avatar" src="${pageContext.request.contextPath}/static/assets/images/other.jpg" alt=""/>
                        </a>
                        <div class="am-comment-main" >
                            <header class="am-comment-hd">
                                <div class="am-comment-meta">
                                    <a ff="nickname" href="#link-to-user" class="am-comment-author">某人</a>
                                    <time ff="msgdate" datetime="" title="">2014-7-12 15:30</time>
                                </div>
                            </header>
                            <div ff="content" class="am-comment-bd">此处是消息内容</div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <!-- 聊天内容发送区域 -->
        <div id="EditBox" class="am-g am-g-fixed">
            <!--style给定宽度可以影响编辑器的最终宽度-->
            <script type="text/plain" id="myEditor" style="width:100%;height:140px;"></script>
            <button id="send" type="button" class="am-btn am-btn-primary am-btn-block">走你</button>
        </div>
    </div>
    <script type="text/javascript">
        $(function(){
            //实例化编辑器
            var um = UM.getEditor('myEditor',{
                initialContent:"...",
                autoHeightEnabled:false,
//                toolbar:[
//                    'source | undo redo | bold italic underline strikethrough | superscript subscript | forecolor backcolor | removeformat |',
//                    'insertorderedlist insertunorderedlist | selectall cleardoc paragraph | fontfamily fontsize' ,
//                    '| justifyleft justifycenter justifyright justifyjustify |',
//                    'link unlink | emotion image video  | map'
//                ]
                toolbar:[
                    'undo redo | bold italic underline strikethrough | superscript subscript | forecolor backcolor | removeformat |',
                    'insertorderedlist insertunorderedlist | selectall cleardoc paragraph | fontfamily fontsize' ,
                    '| justifyleft justifycenter justifyright justifyjustify |'
                ]
            });

            var nickname = "gent"+Math.random();
            var socket = new WebSocket("ws://${pageContext.request.getServerName()}:${pageContext.request.getServerPort()}${pageContext.request.contextPath}/chatServer?username=username");
            //接收服务器的消息
            socket.onmessage=function(ev){
                var obj = eval(   '('+ev.data+')'   );
                addMessage(obj);
            }

            $("#send").click(function(){
                if (!um.hasContents()) {  // 判断消息输入框是否为空
                    // 消息输入框获取焦点
                    um.focus();
                    // 添加抖动效果
                    $('.edui-container').addClass('am-animation-shake');
                    setTimeout("$('.edui-container').removeClass('am-animation-shake')", 1000);
                } else {
                    //获取输入框的内容
                    var txt = um.getContent();
                    //构建一个标准格式的JSON对象
                    var obj = JSON.stringify({
                        nickname:nickname,
                        content:txt
                    });
                    // 发送消息
                    socket.send(obj);
                    // 清空消息输入框
                    um.setContent('');
                    // 消息输入框获取焦点
                    um.focus();
                }
            });
        });

        //人名nickname，时间date，是否自己isSelf，内容content
        function addMessage(msg){

            var box = $("#msgtmp").clone(); 	//复制一份模板，取名为box
            box.show();							//设置box状态为显示
            box.appendTo("#chatContent");		//把box追加到聊天面板中
            box.find('[ff="nickname"]').html(msg.nickname); //在box中设置昵称
            box.find('[ff="msgdate"]').html(msg.date); 		//在box中设置时间
            box.find('[ff="content"]').html(msg.content); 	//在box中设置内容
            box.addClass(msg.isSelf? 'am-comment-flip':'');	//右侧显示
            box.addClass(msg.isSelf? 'am-comment-warning':'am-comment-success');//颜色
//            box.css((msg.isSelf? 'margin-left':'margin-right'),"20%");//外边距

            $("#ChatBox div:eq(0)").scrollTop(999999); 	//滚动条移动至最底部

        }
    </script>
</body>
</html>
