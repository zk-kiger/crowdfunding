<%--
  Created by IntelliJ IDEA.
  User: zk_kiger
  Date: 2019/9/18
  Time: 22:42
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
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 角色维护</a></div>
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
                                <input id="queryTest" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="queryBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button id="deleteBatchBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button type="button" class="btn btn-primary" style="float:right;"
                            onclick="window.location.href='${APP_PATH}/role/add.htm'"><i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="allCheckbox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <ul class="pagination">

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
        queryPageRole(1);
    });

    function pageChange(pageno) {
        queryPageRole(pageno);
    }

    // 分页查询
    var jsonObj = {
        "pageno": 1,
        "pagesize": 5
    };
    var loadingIndex = -1;
    function queryPageRole(pageno) {
        jsonObj.pageno = pageno;
        $.ajax({
            type: "POST",
            data: jsonObj,
            url: "${APP_PATH}/role/doIndex.do",
            beforeSend: function () {
                loadingIndex = layer.load(2, {time: 10 * 1000});
                return true;
            },
            success: function (result) {
                layer.close(loadingIndex);
                if (result.success) {
                    var page = result.page;
                    var datas = page.data;
                    var content = '';
                    $.each(datas, function (i,n) {
                        content += '<tr>';
                        content += '	<td>'+ (i+1) +'</td>';
                        content += '	<td><input type="checkbox" id="'+ n.id +'" name="'+ n.name +'"></td>';
                        content += '	<td>'+ n.name +'</td>';
                        content += '	<td>';
                        content += '		<button type="button" onclick="window.location.href=\'${APP_PATH}/role/assign.htm?id='+ n.id +'\'" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
                        content += '		<button type="button" onclick="window.location.href=\'${APP_PATH}/role/edit.htm?id='+ n.id +'\'" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
                        content += '		<button type="button" onclick="deleteRole('+ n.id +',\''+ n.name +'\')" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
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

    // 查询
    $("#queryBtn").click(function () {
        var queryTest = $("#queryTest").val();
        jsonObj.queryTest = queryTest;
        queryPageRole(1);
    });


    // 异步删除
    function deleteRole(id, name) {

        layer.confirm("确认要删除[" + name + "]角色吗?", {icon: 3, title: '提示'}, function (cindex) {

            $.ajax({
                type: "POST",
                data: {
                    id: id
                },
                url: "${APP_PATH}/role/doDelete.do",
                beforeSend: function () {
                    return true;
                },
                success: function (result) {
                    if (result.success) {
                        window.location.href = "${APP_PATH}/role/index.htm";
                    } else {
                        layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                    }
                }
            });

        }, function (cindex) {
            layer.close(cindex);
        });
    }

    // 复选框全选
    $("#allCheckbox").click(function () {

        var checkStatus = this.checked;

        $("tbody tr td input[type='checkbox']").prop("checked", checkStatus);
    });

    // 批量删除
    $("#deleteBatchBtn").click(function () {

        var selectCheckbox = $("tbody tr td input:checked");

        if (selectCheckbox.length == 0) {
            layer.msg("至少选择一个用户进行删除!", {time: 1000, icon: 5, shift: 6});
            return false;
        }

        var jsonObj = {};
        $.each(selectCheckbox, function (i, n) {
            jsonObj["roles["+ i +"].id"] = n.id;
            jsonObj["roles["+ i +"].name"] = n.name;
        });
        layer.confirm("确认要删选中角色吗?", {icon: 3, title: '提示'}, function (cindex) {

            $.ajax({
                type: "POST",
                // 如果需要传数组时，可以使用 url?id=5&id=6&7的形式
                // data: idStr,
                data: jsonObj,
                url: "${APP_PATH}/role/deleteBatch.do",
                beforeSend: function () {
                    return true;
                },
                success: function (result) {
                    if (result.success) {
                        window.location.href = "${APP_PATH}/role/index.htm";
                    } else {
                        layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                    }
                }
            });

        }, function (cindex) {
            layer.close(cindex);
        });
    });


</script>
</body>
</html>

