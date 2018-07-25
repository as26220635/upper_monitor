<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2017/3/6
  Time: 19:44
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form id="editPasswordForm">
    <div class="form-group has-feedback">
        <input type="password" class="form-control" placeholder="旧密码" id="oldPassword" name="oldPassword">
        <a href="javascript:void(0)" class="glyphicon glyphicon-eye-open form-control-feedback"
           style="pointer-events: auto;"></a>
    </div>
    <div class="form-group has-feedback">
        <input type="password" class="form-control" placeholder="新密码" id="password" name="password">
        <a href="javascript:void(0)" class="glyphicon glyphicon-eye-open form-control-feedback"
           style="pointer-events: auto;"></a>
    </div>
    <div class="form-group has-feedback">
        <input type="password" class="form-control" placeholder="确认新密码" id="confirmPassword" name="confirmPassword">
        <a href="javascript:void(0)" class="glyphicon glyphicon-eye-open form-control-feedback"
           style="pointer-events: auto;"></a>
    </div>
</form>
<script>
    $(".select2").select2({language: "zh-CN"});

    //显示密码
    $("#editPasswordForm a").click(function () {
        var pswd = $(this).siblings("input");
        if ($(this).hasClass("glyphicon-eye-open")) {
            pswd.prop("type", "text");
            $(this).removeClass("glyphicon-eye-open").addClass("glyphicon-eye-close");
        } else {
            pswd.prop("type", "password");
            $(this).removeClass("glyphicon-eye-close").addClass("glyphicon-eye-open");
        }
    });

    validator.init({
        form: $('#editPasswordForm'),
        fields: {
            oldPassword: {
                message: '密码验证失败',
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 16,
                        message: '密码长度必须在6到16位之间'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 16,
                        message: '新密码长度必须在6到16位之间'
                    },
                    identical: {
                        field: 'confirmPassword',
                        message: '密码和确认密码不一致'
                    }
                }
            },
            confirmPassword: {
                validators: {
                    notEmpty: {
                        message: '确认密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 16,
                        message: '确认密码长度必须在6到16位之间'
                    },
                    identical: {
                        field: 'password',
                        message: '密码和确认密码不一致'
                    }
                }
            },

        }
    });

</script>