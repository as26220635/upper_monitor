<%--
  Created by IntelliJ IDEA.
  User: 余庚鑫
  Date: 2017/3/6
  Time: 19:44
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form id="editUserForm">
    <div class="form-group has-feedback">
        <label>真实姓名:</label>
        <input type="text" class="form-control" placeholder="手机号" id="SAI_NAME" name="SAI_NAME"
               value="${accountInfo.SAI_NAME}" minlength="2" maxlength="12">
    </div>
    <div class="form-group has-feedback">
        <label>手机:</label>
        <input type="text" class="form-control" placeholder="手机号" id="SAI_PHONE" name="SAI_PHONE"
               value="${accountInfo.phone}">
    </div>
    <div class="form-group has-feedback">
        <label>邮箱:</label>
        <input type="text" class="form-control" placeholder="邮箱" id="SAI_EMAIL" name="SAI_EMAIL"
               value="${accountInfo.mail}">
    </div>
</form>

<script>
    $(".select2").select2({language: "zh-CN"});

    validator.init({
        form: $('#editUserForm'),
        fields: {
            phone: {
                message: '手机号码验证失败',
                validators: {
                    notEmpty: {
                        message: '手机号码为空'
                    },
                    regexp: {
                        regexp: phoneReg,
                        message: '手机号码格式有误'
                    }
                }
            },
            mail: {
                validators: {
                    notEmpty: {
                        message: '邮箱不能为空'
                    },
                    emailAddress: {
                        message: '邮箱地址格式有误'
                    }
                }
            }
        }
    });

</script>