<%--
  Created by IntelliJ IDEA.
  User: zk_kiger
  Date: 2019/10/7
  Time: 22:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/main.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor: pointer;
        }

        table tbody tr:nth-child(odd) {
            background: #F4F4F4;
        }

        table tbody td:nth-child(even) {
            color: #C00;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 流程管理</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <%@
                        include file="/WEB-INF/views/common/top.jsp"
                %>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <div class="tree">
                <%@
                        include file="/WEB-INF/views/common/menu.jsp"
                %>
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="queryTest" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="queryBtn" type="button" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>

                    <button id="uploadPrcDefBtn" type="button" class="btn btn-primary" style="float:right;"><i
                            class="glyphicon glyphicon-upload"></i>
                        上传流程定义文件
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">

                        <form id="deployForm" action="" method="post" enctype="multipart/form-data">
                            <input id="processDefFile" style="display: none" type="file" name="processDefFile">
                        </form>

                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th>流程定义名称</th>
                                <th>流程定义版本</th>
                                <th>流程定义Key</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <ul class="pagination">
                                        <li class="disabled"><a href="#">上一页</a></li>
                                        <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
                                        <li><a href="#">2</a></li>
                                        <li><a href="#">3</a></li>
                                        <li><a href="#">4</a></li>
                                        <li><a href="#">5</a></li>
                                        <li><a href="#">下一页</a></li>
                                    </ul>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script type="text/javascript" src="${APP_PATH}/jquery/layer/layer.js"></script>
<script type="text/javascript" src="${APP_PATH}/script/menu.js"></script>
<script src="${APP_PATH }/jquery/jquery-form/jquery-form.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function () {
            if ($(this).find("ul")) {
                $(this).toggleClass("tree-closed");
                if ($(this).hasClass("tree-closed")) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
        showMenu();
        queryPageProcess(1);
    });

    function pageChange(pageno) {
        <%--window.location.href = "${APP_PATH}/user/index.do?pageno="+pageno;--%>
        queryPageProcess(pageno);
    }

    // 给uploadPrcDefBtn按钮绑定点击事件
    $("#uploadPrcDefBtn").click(function () {

        // 表示触发processDefFile的单击事件
        $("#processDefFile").click();
    });

    $("#processDefFile").change(function () {
        var options = {
            url: "${APP_PATH}/process/deploy.do",
            beforeSubmit: function () {
                loadingIndex = layer.msg('数据正在部署中', {icon: 6});
                return true; //必须返回true,否则,请求终止.
            },
            success: function (result) {
                layer.close(loadingIndex);
                if (result.success) {
                    layer.msg("数据部署成功", {time: 1000, icon: 6});
                    queryPageProcess(1);
                } else {
                    layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                }
            }
        };

        $("#deployForm").ajaxSubmit(options); //异步提交
        return;
    });

    var jsonObj = {
        "pageno": 1,
        "pagesize": 10
    };

    var loadingIndex = -1;

    function queryPageProcess(pageno) {
        jsonObj.pageno = pageno;
        $.ajax({
            type: "POST",
            data: jsonObj,
            url: "${APP_PATH}/process/doIndex.do",
            beforeSend: function () {
                loadingIndex = layer.load(2, {time: 10 * 1000});
                return true;
            },
            success: function (result) {
                layer.close(loadingIndex);
                if (result.success) {
                    var page = result.page;
                    var data = page.data;

                    var content = '';

                    $.each(data, function (i, n) {
                        content += '<tr>';
                        content += '	<td>' + (i + 1) + '</td>';
                        content += '	<td>' + n.name + '</td>';
                        content += '	<td>' + n.version + '</td>';
                        content += '	<td>' + n.key + '</td>';
                        content += '	<td>';
                        content += '		<button type="button" onclick="window.location.href=\'${APP_PATH}/process/showimg.do?id='+ n.id +'\'" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-eye-open"></i></button>';
                        content += '		<button type="button" onclick="deleteProDef(\''+ n.id +'\',\''+ n.name +'\')" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
                        content += '	</td>';
                        content += '</tr>';
                    });

                    $("tbody").html(content);


                    var contentBar = '';

                    if (page.pageno == 1) {
                        contentBar += '<li class="disabled"><a href="#">上一页</a></li>';
                    } else {
                        contentBar += '<li><a href="#" onclick="pageChange(' + (page.pageno - 1) + ')">上一页</a></li>';
                    }

                    for (var i = 1; i <= page.totalno; i++) {
                        contentBar += '<li';
                        if (page.pageno == i) {
                            contentBar += ' class="active"';
                        }
                        contentBar += '><a href="#" onclick="pageChange(' + i + ')">' + i + '</a></li>';
                    }


                    if (page.pageno == page.totalno) {
                        contentBar += '<li class="disabled"><a href="#">下一页</a></li>';
                    } else {
                        contentBar += '<li><a href="#" onclick="pageChange(' + (page.pageno + 1) + ')">下一页</a></li>';
                    }

                    $(".pagination").html(contentBar);

                } else {
                    layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                }
            }
        });
    }

    $("#queryBtn").click(function () {
        var queryTest = $("#queryTest").val();
        jsonObj.queryTest = queryTest;
        queryPageProcess(1);
    });


    function deleteProDef(id, name) {

        layer.confirm("确认要删除[" + name + "]流程定义吗?", {icon: 3, title: '提示'}, function (cindex) {

            $.ajax({
                type: "POST",
                data: {
                    id: id
                },
                url: "${APP_PATH}/process/doDelete.do",
                beforeSend: function () {
                    return true;
                },
                success: function (result) {
                    layer.close(cindex);
                    if (result.success) {
                        queryPageProcess(1);
                    } else {
                        layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                    }
                }
            });

        }, function (cindex) {
            layer.close(cindex);
        });

    }
</script>
</body>
</html>

