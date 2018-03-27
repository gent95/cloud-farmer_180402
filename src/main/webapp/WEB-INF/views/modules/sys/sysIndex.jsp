<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<html>
<head>
    <meta name="decorator" content="blank"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>${fns:getConfig('productName')}</title>
    <link rel="stylesheet" href="${ctxStatic}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctxStatic}/css/cssreset-min.css"/>
    <link rel="stylesheet" href="${ctxStatic}/css/animate.min.css"/>
    <link rel="stylesheet" href="${ctxStatic}/css/ui.css"/>
    <link rel="stylesheet" href="${ctxStatic}/css/main.css"/>
    <script src="${ctxStatic}/jquery/jquery-1.9.1.js"></script>
    <c:set var="tabmode"
           value="${empty cookie.tabmode.value ? '0' : cookie.tabmode.value}"/>
    <c:if test="${tabmode eq '1'}">
        <link rel="Stylesheet" href="${ctxStatic}/jerichotab/css/jquery.jerichotab.css"/>
        <script type="text/javascript" src="${ctxStatic}/jerichotab/js/jquery.jerichotab.js"></script>
    </c:if>

    <script type="text/javascript">
        $(document).ready(function () {
            //  loginName=$("#userloginName").val();
            dispTime();
            <c:if test="${tabmode eq '1'}">
            // 初始化页签
            $.fn.initJerichoTab({
                renderTo: '#right', uniqueId: 'jerichotab',
                contentCss: {'height': $('#right').height() - tabTitleHeight},
                tabs: [], loadOnce: true, tabWidth: 110, titleHeight: tabTitleHeight
            });//</c:if>
            // 绑定菜单单击事件
            $("#menu a.menu").click(function () {

                // 一级菜单焦点
                $("#menu li.menu").removeClass("active");
                $("#menu li.menu").removeClass("selected");
                $(this).parent().addClass("selected");

                // 左侧区域隐藏
                if ($(this).attr("target") == "mainFrame") {
                    $("#left,#openClose").hide();
                    wSizeWidth();
                    // <c:if test="${tabmode eq '1'}"> 隐藏页签
                    $(".jericho_tab").hide();
                    $("#mainFrame").show();//</c:if>
                    return true;
                }
                // 左侧区域显示
                $("#left,#openClose").show();
                if (!$("#openClose").hasClass("close")) {
                    $("#openClose").click();
                }
                // 显示二级菜单
                var menuId = "#menu-" + $(this).attr("data-id");
                if ($(menuId).length > 0) {
                    $("#left .accordion").hide();
                    $(menuId).show();
                    // 初始化点击第一个二级菜单
                    if (!$(menuId + " .accordion-body:first").hasClass('in')) {
                        $(menuId + " .accordion-heading:first a").click();
                    }
                    if (!$(menuId + " .accordion-body li:first ul:first").is(":visible")) {
                        $(menuId + " .accordion-body a:first i").click();
                    }
                    // 初始化点击第一个三级菜单
                    $(menuId + " .accordion-body li:first li:first a:first i").click();
                } else {
                    // 获取二级菜单数据
                    $.get($(this).attr("data-href"), function (data) {
                        if (data.indexOf("id=\"loginForm\"") != -1) {
                            alert('未登录或登录超时。请重新登录，谢谢！');
                            // layer.msg("账号已在异地登录！！请注意！！");
                            top.location = "${ctx}";
                            return false;
                        }
                        $("#left .accordion").hide();
                        $("#left").append(data);
                        // 链接去掉虚框
                        $(menuId + " a").bind("focus", function () {
                            if (this.blur) {
                                this.blur()
                            }
                            ;
                        });
                        // 二级标题
                        $(menuId + " .accordion-heading a").click(function () {
                            $('.nav-list li a').removeClass('active')
                        });
                        // 二级内容
                        $(menuId + " .accordion-body a").click(function () {
                            $(menuId + " li").removeClass("active");
                            $(menuId + " li i").removeClass("icon-white");
                            $(this).parent().addClass("active");
                            $(this).children("i").addClass("icon-white");

                            $(".icon-chevron-down").remove();
                            $(".icon-chevron-right").remove();
                        });
                        // 展现三级
                        $(menuId + " .accordion-inner a").click(function () {
                            $('.nav-list li a').removeClass('active')
                            $(this).addClass('active')
                            closeNav()
                            var href = $(this).attr("data-href");
                            if ($(href).length > 0) {
                                $(href).toggle().parent().toggle();
                                return false;
                            }
                            // <c:if test="${tabmode eq '1'}"> 打开显示页签
                            return addTab($(this)); // </c:if>
                        });
                        // 默认选中第一个菜单
                        $(menuId + " .accordion-body a:first i").click();
                        $(menuId + " .accordion-body li:first li:first a:first i").click();

                        /*非管理员删除导航*/
                        if (!${fns:ifAdmin()}) {
                            $("#menu").remove();
                        }
                    });
                }
                // 大小宽度调整
//                wSizeWidth();
                return false;
            });
            // 初始化点击第一个一级菜单
            $("#menu a.menu:first span").click();
            // <c:if test="${tabmode eq '1'}"> 下拉菜单以选项卡方式打开
            $("#userInfo .dropdown-menu a").mouseup(function () {
                return addTab($(this), true);
            });// </c:if>
            // 鼠标移动到边界自动弹出左侧菜单
            $("#openClose").mouseover(function () {
                if ($(this).hasClass("open")) {
                    $(this).click();
                }
            });
            // 获取通知数目  <c:set var="oaNotifyRemindInterval" value="${fns:getConfig('oa.notify.remind.interval')}"/>
            function getNotifyNum() {
                $.get("${ctx}/oa/oaNotify/self/count?updateSession=0&t=" + new Date().getTime(), function (data) {
                    // $.get("${ctx}/oa/oaNotify/self/returnCount?updateSession=0&t=" + new Date().getTime(), function (data) {
                    var num = parseFloat(data);
                    if (num > 0) {
                        $("#notifyNum,#notifyNum2").show().html("(" + num + ")");
                    } else {
                        $("#notifyNum,#notifyNum2").hide()
                    }
                });
            }

            getNotifyNum(); //<c:if test="${oaNotifyRemindInterval ne '' && oaNotifyRemindInterval ne '0'}">
            setInterval(getNotifyNum, ${oaNotifyRemindInterval}); //</c:if>
            $(".accordion").after("<div style='font-size: 10px; width: 155px; margin-bottom: 0px; margin-top: 90%;'> Copyright &copy;${fns:getConfig('productName')}</div>");

                /**
                 * 手机导航
                 */
                function openNav(){
                    $('.navbar-static-side').addClass('on').removeClass('off')
                    $('.mask').fadeIn()
                }
                function closeNav(){
                    $('.navbar-static-side').addClass('off').removeClass('on')
                    $('.mask').fadeOut()
                }
                $('.navbar-toggle').click(function(){
                    openNav()
                })
                $('.nav-close').click(function(){
                    closeNav()
                })
                $('.mask').click(function(){
                    closeNav()
                })
                $('body').addClass('fixed-sidebar full-height-layout ').css('overflow', 'hidden;')
                $('.dropdown-menu li a').each(function(){
                    $(this).click(function(){
                        closeNav()
                    })
                })

                /**
                 * 菜单touch滑动
                 */
                var startX,startY,moveEndX,moveEndY,X,Y
                $("body").on("touchstart", function(e) {
                    startX = e.originalEvent.changedTouches[0].pageX
                    startY = e.originalEvent.changedTouches[0].pageY
                })
                $("body").on("touchend", function(e) {
                    moveEndX = e.originalEvent.changedTouches[0].pageX
                    moveEndY = e.originalEvent.changedTouches[0].pageY
                    X = moveEndX - startX, Y = moveEndY - startY;
                    //左滑
                    if ( X >= 50) {
                        openNav()
                    }
                    //右滑
                    else if ( X <= -50 ) {
                        closeNav()
                    }
                })

        });
        function dispTime() {
            var nowDate = new Date();
            var hour = nowDate.getHours();
            var minu = nowDate.getMinutes();
            if (minu < 10) {
                minu = "0" + minu;
            }
            var time = hour + ":" + minu;
            document.getElementById("Time").innerHTML = time;
            var myTime = setTimeout("dispTime()", 10000);
        }
        // <c:if test="${tabmode eq '1'}"> 添加一个页签
        function addTab($this, refresh) {
            $(".jericho_tab").show();
            $("#mainFrame").hide();
            $.fn.jerichoTab.addTab({
                tabFirer: $this,
                title: $this.text(),
                closeable: true,
                data: {
                    dataType: 'iframe',
                    dataLink: $this.attr('href')
                }
            }).loadData(refresh);
            return false;
        }// </c:if>
    </script>
</head>
<body>

<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="sidebar-collapse">
            <div id="userInfo" class="dropdown">
                <button class="nav-close" type="button">
                    <img src="/static/images/icon-close.png" alt="">
                </button>
                <c:choose>
                    <c:when test="${fns:getUser().photo!=null&&fns:getUser().photo!=''}">
                        <img src="${fns:getUser().photo}"/>
                    </c:when>
                    <c:otherwise>
                        <img src="${ctxStatic}/images/homePage/tuxiang.png"
                             style="height: 89px; width: 89px;border-radius: 50%; "/>
                        <br/>
                    </c:otherwise>
                </c:choose>
                <a class="dropdown-toggle" data-toggle="dropdown" href="#" title="个人信息">
                    <span>${fns:getUser().name}</span>
                    <span id="notifyNum" class="label label-info hide"></span>
                </a>
                <ul class="dropdown-menu animated fadeInUp">
                    <li><a href="${ctx}/sys/user/info" target="mainFrame"><i class="icon-user"></i>&nbsp; 个人信息</a>
                    </li>
                    <li><a href="${ctx}/sys/user/modifyPwd" target="mainFrame">
                        <i class="icon-lock"></i>&nbsp; 修改密码</a></li>
                    <li><a href="${ctx}/message/waringMessage" target="mainFrame"><i class="icon-bell"></i>&nbsp;
                        通知预警 </a></li>
                    <li>
                        <a href="${ctx}/oa/oaNotify/self" target="mainFrame">
                            <i class="icon-bell"></i>&nbsp; 我的通知
                            <span id="notifyNum2" class="label label-info hide"></span>
                        </a>
                    </li>
                    <li><a href="${ctx}/logout"><i class="icon-remove-sign"></i>&nbsp; 退出登陆</a></li>
                </ul>
            </div>
            <div id="left"></div>
        </div>
    </nav>
    <!--左侧导航结束-->
    <!--遮罩层-->
    <div class="mask"></div>
    <!--右侧部分开始-->
    <div id="page-wrapper" class="dashbard-1">
        <div class="row">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="page-header" role="navigation">
                    <button class="navbar-toggle" type="button">
                        <img src="/static/images/icon-bars.png" alt="">
                    </button>
                    <a class="navbar-brand" href="${ctx}/grow/goBack"><img src="/static/images/03.png" alt="" class="img-responsive"></a>
                    <ul class="nav navbar-nav">
                        <li>
                            <div class="weather hidden-xs">
                                <a href="http://www.nmc.cn/" target="_blank"> <img src="/static/js/regions/img/fog.png"></a>
                                <span class="temp">${fns:getTemperature().get("temperature") }°</span>
                                <%--<div style="font-size: 16px; font-weight: bold; margin-top: 60px;margin-left: 250px;"><a href="${ctx}/grow/goBack">首页</a></div>--%>
                            </div>
                        </li>
                        <li>
                            <ul id="menu" class="shortcut">
                                <li><a href="${ctx}/grow/goBack">首页</a></li>
                                <c:set var="firstMenu" value="true"/>
                                <c:forEach items="${fns:getMenuList()}" var="menu"
                                           varStatus="idxStatus">
                                    <c:if test="${menu.parent.id eq '1'&&menu.isShow eq '1'}">
                                        <li
                                                class="menu ${not empty firstMenu && firstMenu ? ' active' : ''}">
                                            <c:if test="${empty menu.href}">
                                                <a class="menu " href="javascript:"
                                                   data-href="${ctx}/sys/menu/tree?parentId=${menu.id}"
                                                   data-id="${menu.id}"><span>${menu.name}</span></a>
                                            </c:if> <c:if test="${not empty menu.href}">
                                            <a class="menu"
                                               href="${fn:indexOf(menu.href, '://') eq -1 ? ctx : ''}${menu.href}"
                                               data-id="${menu.id}" target="mainFrame"><span>${menu.name}</span></a>
                                        </c:if>
                                        </li>
                                        <c:if test="${firstMenu}">
                                            <c:set var="firstMenuId" value="${menu.id}"/>
                                        </c:if>
                                        <c:set var="firstMenu" value="false"/>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right hidden-xs">
                        <li>
                            <ul id="userControl" class="nav pull-right">
                                <div>
                                    <p><%=new SimpleDateFormat("yyyy/MM/dd ").format(new Date())%>
                                    </p>
                                    <p id="Time"></p>
                                </div>
                            </ul>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
        <div id="right" class="row J_mainContent">
            <iframe id="mainFrame" name="mainFrame" class="J_iframe" width="100%" height="100%" src="" frameborder="0" seamless></iframe>
        </div>
    </div>
    <!--右侧部分结束-->
</div>
</body>
</html>