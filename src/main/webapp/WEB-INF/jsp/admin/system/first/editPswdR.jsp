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
<style>
    .form-control-feedback {
        opacity: 1;

    }

    .mdi {
        font-size: 19px;
    }

    .has-feedback label ~ .form-control-feedback {
        top: 0;
    }
</style>
<form id="editForm">
    <input type="hidden" id="editAciveUserPasswordId" value="${activeUser.userid}">

    <div class="form-group label-floating has-feedback">
        <label class="control-label">旧密码</label>
        <input type="password" class="form-control" id="oldPassword" name="oldPassword">
        <a href="javascript:void(0)" class="mdi mdi-eye-outline form-control-feedback"
           style="pointer-events: auto;"></a>
    </div>
    <div class="form-group label-floating has-feedback">
        <label class="control-label">新密码</label>
        <input type="password" class="form-control" id="password" name="password">
        <a href="javascript:void(0)" class="mdi mdi-eye-outline form-control-feedback"
           style="pointer-events: auto;"></a>
    </div>
    <div class="form-group label-floating has-feedback">
        <label class="control-label">确认新密码</label>
        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword">
        <a href="javascript:void(0)" class="mdi mdi-eye-outline form-control-feedback"
           style="pointer-events: auto;"></a>
    </div>
</form>

<script>
    //显示密码
    $("#editForm a").click(function () {
        var pswd = $(this).siblings("input");
        if ($(this).hasClass("mdi-eye-outline")) {
            pswd.prop("type", "text");
            $(this).removeClass("mdi-eye-outline").addClass("mdi-eye-off-outline");
        } else {
            pswd.prop("type", "password");
            $(this).removeClass("mdi-eye-off-outline").addClass("mdi-eye-outline");
        }
    });

    validator.init({
        form: $('#editForm'),
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